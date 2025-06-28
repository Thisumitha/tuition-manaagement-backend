package org.example.service.impl;

import org.example.dto.AttendanceDto;
import org.example.entity.AttendanceEntity;
import org.example.entity.HallEntity;
import org.example.entity.StudentEntity;
import org.example.entity.TeacherEntity;
import org.example.repository.AttendanceRepository;
import org.example.repository.HallRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.example.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override

    public AttendanceDto markAttendance(AttendanceDto dto) {
        HallEntity cls = hallRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + dto.getClassId()));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        boolean alreadyMarked = attendanceRepository.existsByStudentIdAndClassIdAndTimestampBetween(
                dto.getStudentId(), cls.getId(), startOfDay, endOfDay
        );

        if (alreadyMarked) {
            throw new IllegalStateException("Attendance has already been marked for this student in this class today.");
        }

        int totalAttendances = attendanceRepository.countByStudentIdAndClassId(dto.getStudentId(), cls.getId());

        // If this is 5th, 9th, 13th... attendance, require payment first
        if (totalAttendances >= 4 && totalAttendances % 4 == 0) {
            StudentEntity student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            double classFee = cls.getClassFee();
            if (student.getWallet() < classFee) {
                throw new IllegalStateException("Please pay class fee to continue attending.");
            }

            // Deduct fee from student
            student.setWallet(student.getWallet() - classFee);
            studentRepository.save(student);

            // Pay teacher after deducting hall fee
            TeacherEntity teacher = teacherRepository.findById(cls.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            double hallFeeAmount = (cls.getHallFeePercentage() / 100.0) * classFee;
            double teacherPayment = classFee - hallFeeAmount;

            teacher.setWallet(teacher.getWallet() + teacherPayment);
            teacherRepository.save(teacher);
        }

        // Proceed to mark attendance
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setStudentId(dto.getStudentId());
        attendance.setClassId(cls.getId());
        attendance.setTimestamp(LocalDateTime.now());

        AttendanceEntity saved = attendanceRepository.save(attendance);

        AttendanceDto response = mapper.map(saved, AttendanceDto.class);
        response.setClassName(cls.getClassName());
        return response;
    }


    @Override
    public List<AttendanceDto> getAttendanceByStudentAndClass(Long studentId, Long classId) {
        return attendanceRepository.findByStudentIdAndClassId(studentId, classId)
                .stream()
                .map(entity -> mapper.map(entity, AttendanceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getAttendanceCountForStudentInClass(Long studentId, Long classId) {
        return attendanceRepository.countByStudentIdAndClassId(studentId, classId);
    }

    @Override
    public List<AttendanceDto> getAttendanceForClassOnDate(Long classId, String dateString) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.", e);
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return attendanceRepository.findByClassIdAndTimestampBetween(classId, startOfDay, endOfDay)
                .stream()
                .map(entity -> mapper.map(entity, AttendanceDto.class))
                .collect(Collectors.toList());
    }
}

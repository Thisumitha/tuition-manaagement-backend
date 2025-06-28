package org.example.service;

import org.example.dto.AttendanceDto;
import java.util.List;

public interface AttendanceService {
    AttendanceDto markAttendance(AttendanceDto dto);
    List<AttendanceDto> getAttendanceByStudentAndClass(Long studentId, Long classId);
    int getAttendanceCountForStudentInClass(Long studentId, Long classId);
    List<AttendanceDto> getAttendanceForClassOnDate(Long classId, String date);

}

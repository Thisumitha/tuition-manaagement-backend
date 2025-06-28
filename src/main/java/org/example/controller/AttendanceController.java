package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AttendanceDto;
import org.example.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 1. Mark attendance (RequestBody is not affected by this issue)
    @PostMapping
    public AttendanceDto markAttendance(@RequestBody AttendanceDto dto) {
        return attendanceService.markAttendance(dto);
    }

    // 2. Get attendance count for a student in a class
    @GetMapping("/count")
    public int getAttendanceCount(
            // Explicitly name the parameters
            @RequestParam("studentId") Long studentId,
            @RequestParam("classId") Long classId) {
        return attendanceService.getAttendanceCountForStudentInClass(studentId, classId);
    }

    // 3. Get all attendance records for a student in a class
    @GetMapping("/records")
    public List<AttendanceDto> getAttendanceRecords(
            // Explicitly name the parameters
            @RequestParam("studentId") Long studentId,
            @RequestParam("classId") Long classId) {
        return attendanceService.getAttendanceByStudentAndClass(studentId, classId);
    }

    // 4. Get attendance for a class on a specific date
    @GetMapping("/by-date")
    public List<AttendanceDto> getAttendanceByDate(
            // Explicitly name the parameters
            @RequestParam("classId") Long classId,
            @RequestParam("date") String date
    ) {
        return attendanceService.getAttendanceForClassOnDate(classId, date);
    }
}
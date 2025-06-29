package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TeacherDto;
import org.example.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public TeacherDto createTeacher(@RequestBody TeacherDto dto) {
        return teacherService.createTeacher(dto);
    }

    @PutMapping("/{id}")
    public TeacherDto updateTeacher(@PathVariable Long id, @RequestBody TeacherDto dto) {
        return teacherService.updateTeacher(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    @GetMapping("/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping
    public List<TeacherDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<String> payTeacher(@PathVariable Long id, @RequestParam double amount) {
        try {
            teacherService.payTeacher(id, amount);
            return ResponseEntity.ok("✅ Teacher ID " + id + " paid amount $" + amount);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ Payment failed: " + e.getMessage());
        }
    }
    @GetMapping("/wallets")
    public ResponseEntity<List<TeacherDto>> getAllWithWallets() {
        return ResponseEntity.ok(teacherService.getAllTeachersWithWallets());
    }
    @GetMapping("/wallets/total")
    public ResponseEntity<Double> getTotalPendingPayments() {
        double total = teacherService.getAllTeachersWithWallets()
                .stream()
                .mapToDouble(TeacherDto::getWallet)
                .sum();
        return ResponseEntity.ok(total);
    }


}

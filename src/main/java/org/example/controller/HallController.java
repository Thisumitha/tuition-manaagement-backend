package org.example.controller;

import org.example.dto.HallDto;
import org.example.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
@CrossOrigin // Enable if accessing from frontend
public class HallController {

    @Autowired
    private HallService hallService;

    @PostMapping
    public HallDto createHall(@RequestBody HallDto dto) {
        return hallService.saveHall(dto);
    }

    @GetMapping
    public List<HallDto> getAllHalls() {
        return hallService.getAllHalls();
    }

    @GetMapping("/{id}")
    public HallDto getHallById(@PathVariable Long id) {
        return hallService.getHallById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
    }
}

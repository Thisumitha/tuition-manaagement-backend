package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.HallDto;
import org.example.service.HallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

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
    public void deleteHall(@PathVariable("id") Long id) {
        hallService.deleteHall(id);
    }
}

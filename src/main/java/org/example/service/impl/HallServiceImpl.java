package org.example.service.impl;

import lombok.Builder;
import org.example.dto.HallDto;
import org.example.entity.HallEntity;
import org.example.repository.HallRepository;
import org.example.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Builder
@Service
public class HallServiceImpl implements HallService {

    @Autowired
    private HallRepository hallRepository;

    @Override
    public HallDto saveHall(HallDto dto) {
        HallEntity entity = HallEntity.builder()
                .className(dto.getClassName())
                .teacher(dto.getTeacher())
                .students(dto.getStudents())
                .day(dto.getDay())
                .timeSlot(dto.getTimeSlot())
                .build();

        HallEntity saved = hallRepository.save(entity);

        return HallDto.builder()
                .id(saved.getId())
                .className(saved.getClassName())
                .teacher(saved.getTeacher())
                .students(saved.getStudents())
                .day(saved.getDay())
                .timeSlot(saved.getTimeSlot())
                .build();
    }

    @Override
    public List<HallDto> getAllHalls() {
        return hallRepository.findAll()
                .stream()
                .map(hall -> HallDto.builder()
                        .id(hall.getId())
                        .className(hall.getClassName())
                        .teacher(hall.getTeacher())
                        .students(hall.getStudents())
                        .day(hall.getDay())
                        .timeSlot(hall.getTimeSlot())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public HallDto getHallById(Long id) {
        Optional<HallEntity> optional = hallRepository.findById(id);
        return optional.map(hall -> HallDto.builder()
                        .id(hall.getId())
                        .className(hall.getClassName())
                        .teacher(hall.getTeacher())
                        .students(hall.getStudents())
                        .day(hall.getDay())
                        .timeSlot(hall.getTimeSlot())
                        .build())
                .orElse(null);
    }

    @Override
    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }
}

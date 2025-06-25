package org.example.service.impl;

import org.example.dto.HallDto;
import org.example.entity.HallEntity;
import org.example.repository.HallRepository;
import org.example.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {

    @Autowired
    private HallRepository hallRepository;

    @Override
    public HallDto saveHall(HallDto dto) {
        HallEntity entity = HallEntity.builder()
                .name(dto.getName())
                .capacity(dto.getCapacity())
                .location(dto.getLocation())
                .build();

        HallEntity saved = hallRepository.save(entity);

        return new HallDto(saved.getId(), saved.getName(), saved.getCapacity(), saved.getLocation());
    }

    @Override
    public List<HallDto> getAllHalls() {
        return hallRepository.findAll()
                .stream()
                .map(hall -> new HallDto(hall.getId(), hall.getName(), hall.getCapacity(), hall.getLocation()))
                .collect(Collectors.toList());
    }

    @Override
    public HallDto getHallById(Long id) {
        Optional<HallEntity> optional = hallRepository.findById(id);
        return optional.map(hall -> new HallDto(hall.getId(), hall.getName(), hall.getCapacity(), hall.getLocation()))
                .orElse(null);
    }

    @Override
    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }
}

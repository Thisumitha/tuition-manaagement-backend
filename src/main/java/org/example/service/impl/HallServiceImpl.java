package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.HallDto;
import org.example.entity.HallEntity;
import org.example.repository.HallRepository;
import org.example.service.HallService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final ModelMapper modelMapper;

    @Override
    public HallDto saveHall(HallDto dto) {
        HallEntity hall = modelMapper.map(dto, HallEntity.class);
        HallEntity saved = hallRepository.save(hall);
        return modelMapper.map(saved, HallDto.class);
    }

    @Override
    public List<HallDto> getAllHalls() {
        return hallRepository.findAll().stream()
                .map(h -> modelMapper.map(h, HallDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HallDto getHallById(Long id) {
        return hallRepository.findById(id)
                .map(h -> modelMapper.map(h, HallDto.class))
                .orElse(null);
    }

    @Override
    public void deleteHall(Long id) {
        hallRepository.deleteById(id);
    }
}

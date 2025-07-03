package org.example.service.impl;

import org.example.dto.HallDto;
import org.example.entity.HallEntity;
import org.example.repository.HallRepository;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HallServiceImplTest {
    @Mock
    private HallRepository hallRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private HallServiceImpl hallService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveHall() {
        HallDto dto = HallDto.builder().id(1L).className("Math").build();
        HallEntity entity = HallEntity.builder().id(1L).className("Math").build();
        when(modelMapper.map(dto, HallEntity.class)).thenReturn(entity);
        when(hallRepository.save(entity)).thenReturn(entity);
        when(modelMapper.map(entity, HallDto.class)).thenReturn(dto);
        HallDto result = hallService.saveHall(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllHalls() {
        HallEntity entity = HallEntity.builder().id(1L).className("Math").build();
        HallDto dto = HallDto.builder().id(1L).className("Math").build();
        when(hallRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(modelMapper.map(entity, HallDto.class)).thenReturn(dto);
        List<HallDto> result = hallService.getAllHalls();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetHallById_Found() {
        HallEntity entity = HallEntity.builder().id(1L).className("Math").build();
        HallDto dto = HallDto.builder().id(1L).className("Math").build();
        when(hallRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, HallDto.class)).thenReturn(dto);
        HallDto result = hallService.getHallById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testGetHallById_NotFound() {
        when(hallRepository.findById(1L)).thenReturn(Optional.empty());
        HallDto result = hallService.getHallById(1L);
        assertNull(result);
    }

    @Test
    void testDeleteHall() {
        doNothing().when(hallRepository).deleteById(1L);
        hallService.deleteHall(1L);
        verify(hallRepository, times(1)).deleteById(1L);
    }
}

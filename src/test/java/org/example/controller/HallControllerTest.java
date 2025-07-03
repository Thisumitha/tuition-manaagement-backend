package org.example.controller;

import org.example.dto.HallDto;
import org.example.service.HallService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HallControllerTest {
    @Mock
    private HallService hallService;
    @InjectMocks
    private HallController hallController;

    @Test
    void testCreateHall() {
        HallDto dto = HallDto.builder().id(1L).className("Math").build();
        when(hallService.saveHall(dto)).thenReturn(dto);
        HallDto result = hallController.createHall(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllHalls() {
        HallDto dto = HallDto.builder().id(1L).className("Math").build();
        when(hallService.getAllHalls()).thenReturn(Collections.singletonList(dto));
        List<HallDto> result = hallController.getAllHalls();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetHallById() {
        HallDto dto = HallDto.builder().id(1L).className("Math").build();
        when(hallService.getHallById(1L)).thenReturn(dto);
        HallDto result = hallController.getHallById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testDeleteHall() {
        doNothing().when(hallService).deleteHall(1L);
        hallController.deleteHall(1L);
        verify(hallService, times(1)).deleteHall(1L);
    }
}

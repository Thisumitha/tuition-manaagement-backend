package org.example.service;

import org.example.dto.HallDto;
import java.util.List;

public interface HallService {
    HallDto saveHall(HallDto dto);
    List<HallDto> getAllHalls();
    HallDto getHallById(Long id);
    void deleteHall(Long id);
}

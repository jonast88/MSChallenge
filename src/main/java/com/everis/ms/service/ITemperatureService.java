package com.everis.ms.service;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITemperatureService {

    Mono save(TemperatureDTO e);
    Mono<StadisticDayDTO> findStadisticsByDate(String date);
    Flux<StadisticDTO> findDetailStadisticByDate(String date);
    Flux<Temperature> findAll();
    Flux<Temperature> findAllByDate(String date);
}

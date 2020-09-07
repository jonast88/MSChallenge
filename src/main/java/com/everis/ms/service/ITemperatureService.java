package com.everis.ms.service;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITemperatureService {

    public Mono<Temperature> save(TemperatureDTO e);
    Mono<StadisticDayDTO> findByDate(String date);
    Flux<StadisticDTO> findStadisticByDate(String date);

    Flux<Temperature> getAll();
    Flux<Temperature> findAllByFecha(String date);
}

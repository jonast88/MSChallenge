package com.everis.ms.repository;

import com.everis.ms.entity.Temperature;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TemperatureRepository extends ReactiveCrudRepository<Temperature, Integer> {

    //Mono<Temperature> findById(Integer id);


    //@Query("select id, fecha, temperatura from weather")
    //Flux<Temperature> getAll();

    @Query("select * from weather where TO_CHAR(fecha,'yyyy-MM-dd') = $1")
    Flux<Temperature> findAllByFecha(String fecha);


}
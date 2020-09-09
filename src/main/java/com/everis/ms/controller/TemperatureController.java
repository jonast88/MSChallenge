package com.everis.ms.controller;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import com.everis.ms.service.impl.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class TemperatureController {


    @Autowired
    private TemperatureService temperatureService;

    @RequestMapping(value = { "/create", "/" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono create(@RequestBody TemperatureDTO e) {
        //Mono<Temperature> result= temperatureService.save(e);

        return temperatureService.save(e);
        //flatMap(temperature -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        //                .bodyValue(temperature)).onErrorResume(error -> ServerResponse.badRequest().build());
    }

    @RequestMapping(value = "/stadistics/{date}", method = RequestMethod.GET)
    public ResponseEntity<Mono<StadisticDayDTO>> findByDate(@PathVariable("date") String date) {
        Mono<StadisticDayDTO> e = temperatureService.findByDate(date);
        HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<StadisticDayDTO>>(e, status);
    }

    @RequestMapping(value = "/stadistics/detail/{date}", method = RequestMethod.GET)
    public Flux<StadisticDTO> findStadisticByDate(@PathVariable("date") String date) {
        return temperatureService.findStadisticByDate(date);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Flux<Temperature> findAll() {
        return temperatureService.findAll();
    }

    @RequestMapping(value = "/fecha/{fecha}", method = RequestMethod.GET)
    public Flux<Temperature> findAllByFecha(@PathVariable("fecha") String fecha) {
        return temperatureService.findAllByFecha(fecha);
    }

}
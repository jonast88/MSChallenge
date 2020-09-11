package com.everis.ms.controller;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import com.everis.ms.exception.NotFoundException;
import com.everis.ms.service.impl.TemperatureService;
import com.everis.ms.util.Constantes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.LinkedList;
import java.util.List;

@RestController
@Api(tags = "Application Api REST - Temperature")
public class TemperatureController {


    @Autowired
    private TemperatureService temperatureService;

    /**
     * Description: Registro en la bd la temperatura
     *
     * @param temperatureDTO modelo para registro de temperatura
     * @return GenericResponse<DataDto>
     */
    @ApiOperation(value = "Registrar temperatura con fecha y hora. ",
            response = Temperature.class)

    @RequestMapping(value =  {"/","/create"} , method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono create(@RequestBody TemperatureDTO temperatureDTO) {
        //Mono<Temperature> result= temperatureService.save(e);

        return temperatureService.save(temperatureDTO);
        //flatMap(temperature -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        //                .bodyValue(temperature)).onErrorResume(error -> ServerResponse.badRequest().build());
    }

    @ApiOperation(value = "Obtener todas las temperaturas registradas",
            response = Temperature.class)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Flux<Temperature> findAll() {
        return temperatureService.findAll();
    }

    @ApiOperation(value = "Obtener todas las temperaturas registradas en una fecha determinada",
            response = Temperature.class)
    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public Flux<Temperature> findAllByFecha(
            @ApiParam(name = "date", value = "Fecha a consultar", example = "yyyy-MM-dd")
            @PathVariable("date") String date) {
        return temperatureService.findAllByDate(date);
    }

    /*
    @RequestMapping(value = "/stadistics/{date}", method = RequestMethod.GET)
    public ResponseEntity<Mono<StadisticDayDTO>> findByDate(@PathVariable("date") String date) {
        Mono<StadisticDayDTO> e = temperatureService.findByDate(date);
        HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<StadisticDayDTO>>(e, status);
    }

     */


    @ApiOperation(value = "Obtiene estadisticas de temperaturas en una fecha específica - Celcius",
            response = StadisticDayDTO.class)
    @RequestMapping(value = "/stadistics/{date}", method = RequestMethod.GET)
    public Mono<StadisticDayDTO> findStadisticsByDate(
            @ApiParam(name = "date", value = "Fecha a consultar", example = "yyyy-MM-dd")
            @PathVariable("date") String date) {
        return temperatureService.findStadisticsByDate(date)
                .switchIfEmpty(Mono.error(new NotFoundException(date)));
    }

    @ApiOperation(value = "Obtiene estadisticas de temperaturas en una fecha específica detallado por cada hora",
            response = StadisticDTO.class)
    @RequestMapping(value = "/stadistics-detail/{date}", method = RequestMethod.GET)
    public Flux<StadisticDTO> findDetailStadisticByDate(
            @ApiParam(name = "date", value = "Fecha a consultar", example = "yyyy-MM-dd")
            @PathVariable("date") String date) {
        return temperatureService.findDetailStadisticByDate(date)
                .switchIfEmpty(Mono.error(new NotFoundException(date)));
    }



    @ApiOperation(value = "Obtiene estadisticas de temperaturas en una fecha específica por default se obtiene en Celcius",
            response = StadisticDayDTO.class)
    @RequestMapping(value = "/stadistics/{date}/{type}", method = RequestMethod.GET)
    public Mono<StadisticDayDTO> findStadisticsByDate(
            @ApiParam(name = "date", required = true, value = "Fecha a consultar", example = "yyyy-MM-dd")
            @PathVariable("date") String date,
            @ApiParam(name = "type", required = false, value = "Farenheit (F) de lo contrario la temperatura será devuelta en Celcius", example = "F")
            @PathVariable("type") String type) {


        return temperatureService.findStadisticsByDate(date).map(stadisticDayDTO -> {
            if(type !=null && !type.isEmpty()){

                if(type.equalsIgnoreCase("F")){
                    stadisticDayDTO.setAverage(stadisticDayDTO.getAverage()* Constantes.FARENHEIT);
                    stadisticDayDTO.setMax(stadisticDayDTO.getMax()* Constantes.FARENHEIT);
                    stadisticDayDTO.setMin(stadisticDayDTO.getMin()* Constantes.FARENHEIT);
                }

            }
            return stadisticDayDTO;
        }).switchIfEmpty(Mono.error(new NotFoundException(date)));

    }

    @ApiOperation(value = "Obtiene estadisticas de temperaturas en una fecha específica detallado por cada hora, Celciud por Default",
            response = StadisticDTO.class)
    @RequestMapping(value = "/stadistics-detail/{date}/{type}", method = RequestMethod.GET)
    public Flux<StadisticDTO> findDetailStadisticByDate(
            @ApiParam(name = "date", required = true, value = "Fecha a consultar", example = "yyyy-MM-dd")
            @PathVariable("date") String date,
            @ApiParam(name = "type", required = false, value = "Farenheit (F) de lo contrario la temperatura será devuelta en Celcius", example = "F")
            @PathVariable("type") String type){
        return temperatureService.findDetailStadisticByDate(date)
                .flatMap(stadisticDTO -> {
                    if(type !=null && !type.isEmpty()){

                        if(type.equalsIgnoreCase("F")){
                            stadisticDTO.setAverage(stadisticDTO.getAverage()* Constantes.FARENHEIT);
                            stadisticDTO.setMax(stadisticDTO.getMax()* Constantes.FARENHEIT);
                            stadisticDTO.setMin(stadisticDTO.getMin()* Constantes.FARENHEIT);
                        }

                    }
                    return Flux.just(stadisticDTO);
                })
                .switchIfEmpty(Mono.error(new NotFoundException(date)));
    }


}
package com.everis.ms.controller;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import com.everis.ms.service.impl.TemperatureService;
import com.everis.ms.util.Constantes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TemperatureController.class)
@Import(TemperatureService.class)
class TemperatureControllerTest {

    @MockBean
    private TemperatureService temperatureService;
    @Autowired
    private WebTestClient webClient;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        TemperatureDTO temperatureDTO=new TemperatureDTO();
        temperatureDTO.setFecha("20/09/2020");
        temperatureDTO.setTemperatura(20.5);
        Mockito.when(temperatureService.save(temperatureDTO)).thenReturn(Mono.just(temperatureDTO));

        webClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(temperatureDTO))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(temperatureService).save(temperatureDTO);

    }

    @Test
    void findAll() {
    }

    @Test
    void findAllByFecha() {
        Temperature t = new Temperature();
        t.setTemperature(20.5); t.setId(1);t.setLocaltime(LocalDateTime.now());


        List<Temperature> list = new ArrayList<Temperature>();
        list.add(t);
        new Temperature();
        t.setTemperature(23.5); t.setId(2);t.setLocaltime(LocalDateTime.now());
        list.add(t);


        Flux<Temperature> tmpFlux = Flux.fromIterable(list);

        Mockito
                .when(temperatureService.findAllByDate("2020-09-23"))
                .thenReturn(tmpFlux);

        webClient.get().uri("/{date}", "2020-09-23")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Temperature.class);

        Mockito.verify(temperatureService).findAllByDate("2020-09-23");
    }

    @Test
    void findStadisticsByDate() {
        StadisticDayDTO s = new StadisticDayDTO();
        s.setAverage(20);s.setDate("2020-09-23");s.setMax(29.9);s.setMin(10.1);

        Mockito
                .when(temperatureService.findStadisticsByDate("2020-09-23"))
                .thenReturn(Mono.just(s));

        webClient.get().uri("/stadistics/{date}", "2020-09-23")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.average").isNotEmpty()
                .jsonPath("$.date").isEqualTo("2020-09-23")
                .jsonPath("$.max").isEqualTo(29.9)
                .jsonPath("$.min").isEqualTo(10.1);

        Mockito.verify(temperatureService).findStadisticsByDate("2020-09-23");
    }

    @Test
    void findDetailStadisticByDate() {

        StadisticDTO s = new StadisticDTO();
        s.setTime("19:00 a 19:59");s.setAverage(15.5);s.setMax(30);s.setMin(20);
        List<StadisticDTO> list = new ArrayList<StadisticDTO>();
        list.add(s);
        s = new StadisticDTO();
        s.setTime("20:00 a 20:59");s.setAverage(15.5);s.setMax(30);s.setMin(20);
        list.add(s);


        Flux<StadisticDTO> sFlux = Flux.fromIterable(list);

        Mockito
                .when(temperatureService.findDetailStadisticByDate("2020-09-23"))
                .thenReturn(sFlux);

        webClient.get().uri("/stadistics-detail/{date}", "2020-09-23")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StadisticDTO.class);

        Mockito.verify(temperatureService).findDetailStadisticByDate("2020-09-23");
    }

    @Test
    void testFindStadisticsByDate() {

        StadisticDayDTO s2 = new StadisticDayDTO();
        s2.setDate("2020-09-23");
        s2.setAverage(11*Constantes.FARENHEIT);
        s2.setMax(22*Constantes.FARENHEIT);
        s2.setMin(4*Constantes.FARENHEIT);

        Mockito
                .when(temperatureService.findStadisticsByDate("2020-09-23"))
                .thenReturn(Mono.just(s2));

        webClient.get().uri("/stadistics/{date}/{type}", "2020-09-23","F")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.average").isNotEmpty()
                .jsonPath("$.date").isEqualTo("2020-09-23")
                .jsonPath("$.max").isEqualTo(s2.getMax())
                .jsonPath("$.min").isEqualTo(s2.getMin());

        Mockito.verify(temperatureService).findStadisticsByDate("2020-09-23");
    }

    @Test
    void testFindDetailStadisticByDate() {
        StadisticDTO s2 = new StadisticDTO();
        s2.setTime("19:00 a 19:59");
        s2.setAverage(11*Constantes.FARENHEIT);
        s2.setMax(22*Constantes.FARENHEIT);
        s2.setMin(4*Constantes.FARENHEIT);

        List<StadisticDTO> list = new ArrayList<StadisticDTO>();
        list.add(s2);
        s2 = new StadisticDTO();
        s2.setTime("21:00 a 21:59");
        s2.setAverage(22*Constantes.FARENHEIT);
        s2.setMax(11*Constantes.FARENHEIT);
        s2.setMin(4*Constantes.FARENHEIT);
        list.add(s2);

        Flux<StadisticDTO> sFlux = Flux.fromIterable(list);

        Mockito
                .when(temperatureService.findDetailStadisticByDate("2020-09-23"))
                .thenReturn(sFlux);

        webClient.get().uri("/stadistics-detail/{date}/{type}", "2020-09-23","F")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StadisticDTO.class);

        Mockito.verify(temperatureService).findDetailStadisticByDate("2020-09-23");
    }
}
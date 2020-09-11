package com.everis.ms.service.impl;

import com.everis.ms.controller.TemperatureController;
import com.everis.ms.repository.TemperatureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TemperatureService.class)
@Import(TemperatureRepository.class)
class TemperatureServiceTest {

    @MockBean
    private TemperatureRepository temperatureRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
    }

    @Test
    void findStadisticsByDate() {
    }

    @Test
    void findDetailStadisticByDate() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllByDate() {
    }
}
package com.everis.ms;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.core.DatabaseClient;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MsChallengeApplication {



	public static void main(String[] args) {

		SpringApplication.run(MsChallengeApplication.class, args);


	}

}

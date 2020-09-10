package com.everis.ms.service.impl;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import com.everis.ms.repository.TemperatureRepository;
import com.everis.ms.service.ITemperatureService;
import com.everis.ms.util.Constantes;
import com.everis.ms.util.UtilDate;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TemperatureService implements ITemperatureService {

    @Autowired
    TemperatureRepository temperatureRepository;
    @Qualifier("connectionFactory")
    @Autowired
    ConnectionFactory connectionFactory;

    @Override
    public Mono save(TemperatureDTO e) {
        //Validar si es fecha valida - Fomrato: dd/MM/yyyy HH:mm

        Timestamp timestamp=null;
        Date fecha=null;
        try {
         //   timestamp=UtilDate.parseTimeStampSQL(e.getFecha(), "dd/MM/yyyy HH:mm");
             fecha=UtilDate.parseDate(e.getFecha(), "dd/MM/yyyy HH:mm");
        }catch (Exception ex){
            return Mono.error(new Exception("Error al parsear el formato fecha"));
        }


        Temperature temperature=new Temperature();
        temperature.setMillis(fecha.getTime());
        temperature.setTemperature(e.getTemperatura());
        temperature.setLocaltime(Instant.ofEpochMilli(fecha.getTime())
                .atZone(ZoneId.of(Constantes.REGION))
                .toLocalDateTime());
        //temperature.setId(99);

        return temperatureRepository.save(temperature);
       /* return (Mono<Temperature>) Mono.from(connectionFactory.create())
                .flatMapMany(conn ->
                        conn.createStatement(
                                "INSERT asdf (id, timestamp) VALUES(@id, PENDING_COMMIT_TIMESTAMP())")
                                .bind("id", 1)
                                .add()
                                .execute())
                .blockLast();

        */
    }

    @Override
    public Mono<StadisticDayDTO> findStadisticsByDate(String date) {
        //Validar si es fecha valida - Formato: yyyy-MM-dd
        Date fecha=null;
        try {
            fecha=UtilDate.parseDate(date,Constantes.FORMAT_DATE);
        }catch (Exception e){
            return Mono.error(new Exception("Error en formato de fecha yyyy-MM-dd"));
        }

        Flux<Temperature> temperatureFlux=temperatureRepository.findAllByFecha(date.trim());
        Mono<Double> averageMono = temperatureFlux.collect(Collectors.averagingDouble(Temperature::getTemperature));
        //Promedio
        averageMono.subscribe(System.out::println);

        //Max
        Mono<Optional<Temperature>>  maxMono = temperatureFlux.collect(Collectors.maxBy(Comparator.comparingDouble(Temperature::getTemperature)));
        //maxMono.subscribe(x -> System.out.println(x.get().getTemperature()));
        System.out.println("===MIN==");
        //Min
        Mono<Optional<Temperature>>  minMono = temperatureFlux.collect(Collectors.minBy(Comparator.comparingDouble(Temperature::getTemperature)));
       // minMono.subscribe(x -> System.out.println(x.get().getTemperature()));

        Mono<StadisticDayDTO> x = null;

       return Mono.zip(averageMono,
                maxMono,
                minMono).flatMap(data->{
            StadisticDayDTO stadisticDayDTO= new StadisticDayDTO();
            stadisticDayDTO.setDate(date);
            stadisticDayDTO.setAverage(data.getT1());
            if(data.getT2().isPresent()){
                stadisticDayDTO.setMax(data.getT2().get().getTemperature());
            }
           if(data.getT3().isPresent()){
               stadisticDayDTO.setMin(data.getT3().get().getTemperature());
           }
            return Mono.just(stadisticDayDTO);
        });


    }

    @Override
    public Flux<StadisticDTO> findDetailStadisticByDate(String date) {
        //Validar si es fecha valida - Formato: yyyy-MM-dd
        Date fecha=null;
        try {
            fecha=UtilDate.parseDate(date,Constantes.FORMAT_DATE);
        }catch (Exception e){
            return Flux.error(new Exception("Error en formato de fecha yyyy-MM-dd"));
        }

        Flux<Temperature> temperatureFlux=temperatureRepository.findAllByFecha(date.trim());//.findAllByFecha(date.trim());
        temperatureFlux
                .collect(Collectors.groupingBy(x -> DateTimeFormatter.ofPattern("yyyyMMddHH")
                        .format(x.getLocaltime())))
        .map(stringListMap -> {
            System.out.println(">>>>>>>>"+stringListMap);
            return null;
        });

        Mono<Double> averageMono=temperatureFlux
                .collect(Collectors.groupingBy(x -> DateTimeFormatter.ofPattern("yyyyMMddHH").format(x.getLocaltime())))
                .flatMapIterable(stringListMap -> stringListMap.get("2020092318"))
                .collect(Collectors.averagingDouble(Temperature::getTemperature));
        averageMono.subscribe(System.out::println);

        Mono<Map<Object, List<Temperature>>> flux=temperatureFlux
                .collect(Collectors.groupingBy(x -> DateTimeFormatter.ofPattern("yyyyMMddHH").format(x.getLocaltime())));


        flux.subscribe(objectListMap -> {System.out.println(objectListMap);});



        Flux<List<Temperature>> flux2=temperatureFlux
                .groupBy(x -> DateTimeFormatter.ofPattern("yyyyMMddHH").format(x.getLocaltime()))
                .flatMap(Flux::collectList);

        flux2.subscribe(System.out::println);

        Flux<StadisticDTO> stadisticFlux=flux2.flatMap(temperatures -> {
            Flux<Temperature> tmpFlux=Flux.fromIterable(temperatures);
            Mono<Double> averageTmp=tmpFlux.collect(Collectors.averagingDouble(Temperature::getTemperature));
            Mono<Optional<Temperature>>  maxTmp = tmpFlux.collect(Collectors.maxBy(Comparator.comparingDouble(Temperature::getTemperature)));
            Mono<Optional<Temperature>>  minTmp = tmpFlux.collect(Collectors.minBy(Comparator.comparingDouble(Temperature::getTemperature)));


             return Mono.zip(averageTmp,
                    maxTmp,
                    minTmp).flatMap(data->{
                 StadisticDTO stadisticDTO= new StadisticDTO();
                stadisticDTO.setTime("");
                stadisticDTO.setAverage(data.getT1());
                if(data.getT2().isPresent()){
                    stadisticDTO.setMax(data.getT2().get().getTemperature());
                }
                if(data.getT3().isPresent()){
                    stadisticDTO.setMin(data.getT3().get().getTemperature());
                }
                return Mono.just(stadisticDTO);
            });
        });

        stadisticFlux.subscribe(System.out::println);

         //flux2.subscribe(listOfStringsHavingDataWithSameKey -> System.out.println(listOfStringsHavingDataWithSameKey.toString()));

        //.collect(Collectors.groupingBy(x -> x.getLocaltime().truncatedTo(ChronoUnit.HOURS)));
        //flux.subscribe(objectListMap -> {System.out.println(objectListMap);});

               // .collect(Collectors.groupingBy(x -> DateTimeFormatter.ofPattern("yyyyMMddHH").format(x.getLocaltime())))
       // .subscribe(System.out::println);
        ;


        return stadisticFlux;
    }

    @Override
    public Flux<Temperature> findAll() {
        return temperatureRepository.findAll();
    }
    @Override
    public Flux<Temperature> findAllByDate(String date){
        //Validar si es fecha valida - Formato: yyyy-MM-dd
        Date fecha=null;
        try {
            fecha=UtilDate.parseDate(date,Constantes.FORMAT_DATE);
        }catch (Exception e){
            return Flux.error(new Exception("Error en formato de fecha yyyy-MM-dd"));
        }

        return temperatureRepository.findAllByFecha(date.trim());
    }
    /*
    @Transactional
	public Mono<Customer> save(Customer customer) {

		return repository.save(customer).map(it -> {

			if (it.firstname.equals("Dave")) {
				throw new IllegalStateException();
			} else {
				return it;
			}
		});
	}
     */
}

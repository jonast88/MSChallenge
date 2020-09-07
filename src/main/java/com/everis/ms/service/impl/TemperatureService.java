package com.everis.ms.service.impl;

import com.everis.ms.dto.StadisticDTO;
import com.everis.ms.dto.StadisticDayDTO;
import com.everis.ms.dto.TemperatureDTO;
import com.everis.ms.entity.Temperature;
import com.everis.ms.repository.TemperatureRepository;
import com.everis.ms.service.ITemperatureService;
import com.everis.ms.util.UtilDate;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TemperatureService implements ITemperatureService {

    @Autowired
    TemperatureRepository temperatureRepository;
    @Qualifier("connectionFactory")
    @Autowired
    ConnectionFactory connectionFactory;

    @Override
    public Mono<Temperature> save(TemperatureDTO e) {
        //Validar si es fecha valida - Fomrato: dd/MM/yyyy HH:mm
        Timestamp timestamp=null;
        try {
            timestamp=UtilDate.parseTimeStampSQL(e.getFecha(), "dd/MM/yyyy HH:mm");
        }catch (Exception ex){
            return Mono.error(ex);
        }

        Temperature temperature=new Temperature();
        temperature.setFecha(e.getFecha());
        temperature.setTemperatura(e.getTemperatura());
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
    public Mono<StadisticDayDTO> findByDate(String date) {
       // temperatureRepository.findByDate(UtilDate.parseTimeStampSQL(date));
        return null;
    }

    @Override
    public Flux<StadisticDTO> findStadisticByDate(String date) {
        //temperatureRepository.findAllByDate(UtilDate.parseTimeStampSQL(date));
        return null;
    }

    @Override
    public Flux<Temperature> getAll() {
        return temperatureRepository.getAll();
    }
    @Override
    public Flux<Temperature> findAllByFecha(String date){
        //Validar si es fecha valida - Fomrato: yyyy-MM-dd
        Date fecha=null;
        try {
            fecha=UtilDate.parseDate(date,UtilDate.dateFormatURI);
        }catch (Exception e){
            return Flux.error(e);
        }

        return temperatureRepository.findAllByFecha(date);
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

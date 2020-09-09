package com.everis.ms.entity;

import com.everis.ms.util.Constantes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("weather")
public class Temperature implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Integer id;


    @Column("millis")
    private Long millis;


    @Column("temperature")
    private double temperature;

    @Column("fecha")
    private LocalDateTime localtime;


    /*
    public LocalDateTime getDate() {
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of(Constantes.REGION))
                .toLocalDateTime();
    }

     */


}

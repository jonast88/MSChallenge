package com.everis.ms.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadisticDTO {

    private String time;
    private double min;
    private double max;
    private double average;

}

package com.everis.ms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "DTO para registro de temperaturas")
public class TemperatureDTO {

    @ApiModelProperty(value = "Fecha y Hora de la temperatura",
            notes = "Formato DateTime 24 Horas: dd/MM/yyyy HH:mm",
            example = "26/09/2020 22:00")
    private String fecha;

    @ApiModelProperty(value = "Temperatura en Grados Celcius por Default",
            notes = "Pueden ser en grados negativos o positivos y decimales.",
            example = "-5.20")
    private double temperatura;

}

package de.com.alns.codingtest.hubject.chargingstationdata.services.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "PointLocationDTO", description = "The DTO to hold a geographic coordinate.")
public class PointLocationDTO {

    @Digits(integer = 2, fraction = 6)
    @ApiModelProperty(required = true, value = "The Latitude float number where this Station is located. Pattern: 99.999999")
    private Double latitude;
    
    @Digits(integer = 2, fraction = 6)
    @ApiModelProperty(required = true, value = "The Longitude float number where this Station is located. Pattern: 99.999999")
    private Double longitude;

}

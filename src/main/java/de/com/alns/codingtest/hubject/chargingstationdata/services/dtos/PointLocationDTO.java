package de.com.alns.codingtest.hubject.chargingstationdata.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PointLocationDTO {

    @Digits(integer = 2, fraction = 6)
    private Double latitude;
    @Digits(integer = 2, fraction = 6)
    private Double longitude;

}

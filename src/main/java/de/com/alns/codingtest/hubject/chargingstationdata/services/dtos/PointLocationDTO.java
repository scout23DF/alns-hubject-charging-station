package de.com.alns.codingtest.hubject.chargingstationdata.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PointLocationDTO {

    private Double latitude;
    private Double longitude;

}

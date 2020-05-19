package de.com.alns.codingtest.hubject.chargingstationdata.services.dtos;

import com.sun.istack.NotNull;
import com.vividsolutions.jts.geom.Point;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
public class ChargingStationDTO {

    private Long id;
    private String descriptionChargingStation;
    private Boolean isActive;
    private BigDecimal percentuaEnergyLoaded;
    private Boolean isAvailable;
    private String zipCodeNumber;
    private Point geoLocationPoint;

}

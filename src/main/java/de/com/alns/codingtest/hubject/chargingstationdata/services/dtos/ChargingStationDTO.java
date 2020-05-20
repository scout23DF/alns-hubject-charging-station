package de.com.alns.codingtest.hubject.chargingstationdata.services.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChargingStationDTO {

    private String meaningFullId;
    private String descriptionChargingStation;
    private Boolean isActive;
    private BigDecimal percentuaEnergyLoaded;
    private Boolean isAvailable;
    private String zipCodeNumber;
    private Double latitude;
    private Double longitude;

}

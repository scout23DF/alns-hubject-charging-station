package de.com.alns.codingtest.hubject.chargingstationdata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.HJGeometryUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.locationtech.jts.geom.Point;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@ToString
@Entity(name = "tb_charging_station")
@Validated
@ApiModel(value = "ChargingStation", description = "The entity that represents a Charging Station of Hubject.")
public class ChargingStation {

    @Id
    @NotNull
    @Pattern(regexp = "^[A-Z]{2}(.)[A-Z]{3}(.D)\\d{3}(.S)\\d{4}$")  // DE.BER.D999.S9999  - Test here: https://regex101.com/r/zA9TNK/2
    @Column(name = "meaningfullId", columnDefinition = "char(17)", length = 17)
    @ApiModelProperty(required = true, value = "Identification code of an Charging Station. I suggested to use this convention: [Country Alias].[City Alias].D[District Number].S[Station Number in its District]. Examble: DE.BER.D999.S9999")
    private String meaningfullId;

    @Column(name = "des_charging_station", length = 255)
    @Size(max = 2550)
    @ApiModelProperty(required = false, value = "Description of Location of Charging Station.")
    private String descriptionChargingStation;

    @Column(name = "sta_active")
    @ApiModelProperty(required = false, value = "Status if the Station is Active (Working properly).")
    private Boolean isActive;

    @Column(name = "sta_available")
    @ApiModelProperty(required = false, value = "Status if the Station is Available to support your vehicle right now.")
    private Boolean isAvailable;

    @Column(name = "pct_energy_loaded")
    @Digits(integer = 3, fraction = 2)
    @PositiveOrZero
    @ApiModelProperty(required = false, value = "Percentage of capacity of charge of this Station.")
    private BigDecimal percentuaEnergyLoaded;

    @Column(name = "num_zip_code", length = 5)
    @NotNull
    @Pattern(regexp = "^\\d{5}$")
    @ApiModelProperty(required = true, value = "The ZipCode number where this Station is located. Pattern: 99999")
    private String zipCodeNumber;

    @JsonIgnore
    @NotNull
    @Column(name = "geo_point_location", columnDefinition = "geometry")
    private Point geoLocationPoint;

    @Transient
    public PointLocationDTO getPointLocationDTO() {
        return HJGeometryUtils.convertJtsGeometryToPointLocationDTO(this.getGeoLocationPoint());
    }

    public void setPointLocationDTO(PointLocationDTO pPointLocationDTO) {
        this.setGeoLocationPoint(HJGeometryUtils.convertPointLocationDTOToJtsGeometry(pPointLocationDTO));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChargingStation)) {
            return false;
        }
        return meaningfullId != null && meaningfullId.equals(((ChargingStation) o).meaningfullId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meaningfullId, descriptionChargingStation, percentuaEnergyLoaded, zipCodeNumber);
    }

}

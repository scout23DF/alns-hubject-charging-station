package de.com.alns.codingtest.hubject.chargingstationdata.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.GeometryUtils;
import lombok.Data;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@ToString
@Entity(name = "tb_charging_station")
public class ChargingStation {

    @Id
    @NotNull
    @Pattern(regexp = "^[A-Z]{2}(.)[A-Z]{3}(.D)\\d{3}(.S)\\d{4}$")  // DE.BER.D999.S9999  - Test here: https://regex101.com/r/zA9TNK/2
    @Column(name = "meaningfullId", columnDefinition = "char(17)", length = 17)
    private String meaningfullId;

    @Column(name = "des_charging_station", length = 255)
    @Size(max = 2550)
    private String descriptionChargingStation;

    @Column(name = "sta_active")
    private Boolean isActive;

    @Column(name = "sta_available")
    private Boolean isAvailable;

    @Column(name = "pct_energy_loaded")
    @Digits(integer = 3, fraction = 2)
    @PositiveOrZero
    private BigDecimal percentuaEnergyLoaded;

    @Column(name = "num_zip_code", length = 5)
    @NotNull
    @Pattern(regexp = "^\\d{5}$")
    private String zipCodeNumber;

    @JsonIgnore
    @NotNull
    @Column(name = "geo_point_location", columnDefinition = "geometry")
    private Point geoLocationPoint;

    @Transient
    public PointLocationDTO getPointLocationDTO() {
        return GeometryUtils.convertJtsGeometryToPointLocationDTO(this.getGeoLocationPoint());
    }

    public void setPointLocationDTO(PointLocationDTO pPointLocationDTO) {
        this.setGeoLocationPoint(GeometryUtils.convertPointLocationDTOToJtsGeometry(pPointLocationDTO));
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

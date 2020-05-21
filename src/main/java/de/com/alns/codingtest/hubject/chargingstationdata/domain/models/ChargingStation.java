package de.com.alns.codingtest.hubject.chargingstationdata.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.vividsolutions.jts.geom.Point;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.GeometryUtils;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

@Data
@Entity(name = "tb_charging_station")
public class ChargingStation {

    @Id
    @Column(name = "meaningfullId", columnDefinition = "char(17)", length = 17)  // DE.BER.D999.S9999
    @NotNull
    private String meaningfullId;

    @Column(name = "des_charging_station", length = 255)
    private String descriptionChargingStation;

    @Column(name = "sta_active")
    private Boolean isActive;

    @Column(name = "pct_energy_loaded")
    private BigDecimal percentuaEnergyLoaded;

    @Column(name = "sta_available")
    private Boolean isAvailable;

    @Column(name = "num_zip_code", length = 5)
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
}

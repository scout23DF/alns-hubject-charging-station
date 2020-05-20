package de.com.alns.codingtest.hubject.chargingstationdata.domain.models;

import com.sun.istack.NotNull;
import com.vividsolutions.jts.geom.*;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;

@Data
@Entity(name = "tb_charging_station")
public class ChargingStation {

    @Id
    @Column(name = "meaningFullId")
    @NotNull
    @GeneratedValue
    private String meaningFullId;

    @Column(name = "des_charging_station", length = 255)
    private String descriptionChargingStation;

    @Column(name = "sta_active")
    private Boolean isActive;

    @Column(name = "pct_energy_loaded")
    private BigDecimal percentuaEnergyLoaded;

    @Column(name = "sta_available")
    private Boolean isAvailable;

    @Column(name = "num_zip_code", length = 8)
    private String zipCodeNumber;

    @NotNull
    @Column(name = "geo_point_location", columnDefinition = "geometry")
    private Point geoLocationPoint;

    @Column(name = "geo_polygon_location", columnDefinition = "geometry")
    private MultiPolygon geoLocationPolygon;

    @Column(name = "geo_line_location", columnDefinition = "geometry")
    private LineString geoLocationLine;

}

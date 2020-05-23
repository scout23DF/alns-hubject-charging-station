package de.com.alns.codingtest.hubject.chargingstationdata.models;

import com.sun.istack.NotNull;
import com.vividsolutions.jts.geom.MultiPolygon;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "country")
public class Country {

    @Id
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "iso_a2", length = 2)
    private String iso2;

    @Column(name = "iso_a3", length = 3)
    private String iso3;

    @Column(name = "name", length = 40)
    private String name;

    @NotNull
    @Column(name = "shape", columnDefinition = "geometry")
    private MultiPolygon geometry;

}
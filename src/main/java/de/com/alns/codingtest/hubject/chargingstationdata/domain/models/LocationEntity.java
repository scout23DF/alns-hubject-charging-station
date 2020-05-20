package de.com.alns.codingtest.hubject.chargingstationdata.domain.models;

import com.sun.istack.NotNull;
import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "location")
public class LocationEntity {

    @Id
    @Column(name = "id")
    @NotNull
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", length = 30)
    private String user;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "geometry", columnDefinition = "geometry")
    private Geometry geometry;

}
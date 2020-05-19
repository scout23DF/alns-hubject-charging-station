package de.com.alns.codingtest.hubject.chargingstationdata.repositories;

import com.vividsolutions.jts.geom.Geometry;
import de.com.alns.codingtest.hubject.chargingstationdata.domain.models.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long> {

    @Query("select chgsta from tb_charging_station as chgsta where within(chgsta.geoLocationPoint, :pPerimeterFilter) = TRUE")
    public List<ChargingStation> findWithin(@Param("pPerimeterFilter") Geometry pPerimeterFilter);

}

package de.com.alns.codingtest.hubject.chargingstationdata.services;

import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.ChargingStationDTO;
import org.springframework.stereotype.Service;
import org.wololo.geojson.FeatureCollection;

import java.util.Optional;

@Service
public interface IChargingStationService {

    public Long saveChargingStation(ChargingStationDTO pChargingStationDTO);

    public void updateChargingStation(ChargingStationDTO pChargingStationDTO);

    public void deleteChargingStation(Long pId);

    public Optional<ChargingStationDTO> findLocationById(Long pId);

    public FeatureCollection findAllChargingStations();

    public FeatureCollection findAllChargingStationsWithin(org.wololo.geojson.Geometry pGeoJson);

}

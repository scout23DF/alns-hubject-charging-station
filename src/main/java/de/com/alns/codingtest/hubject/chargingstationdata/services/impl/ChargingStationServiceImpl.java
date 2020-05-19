package de.com.alns.codingtest.hubject.chargingstationdata.services.impl;

import de.com.alns.codingtest.hubject.chargingstationdata.repositories.ChargingStationRepository;
import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.ChargingStationDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wololo.geojson.FeatureCollection;

import java.util.Optional;

@Service
@CommonsLog
public class ChargingStationServiceImpl implements IChargingStationService {

    private ChargingStationRepository chargingStationRepository;

    @Autowired
    public ChargingStationServiceImpl(ChargingStationRepository pRepository) {
        this.chargingStationRepository = pRepository;
    }


    public Long saveChargingStation(ChargingStationDTO pChargingStationDTO) {
        return null;
    };

    public void updateChargingStation(ChargingStationDTO pChargingStationDTO) {

    }

    public void deleteChargingStation(Long pId) {

    }

    public Optional<ChargingStationDTO> findLocationById(Long pId) {
        return null;
    }

    public FeatureCollection findAllChargingStations() {
        return null;
    }

    public FeatureCollection findAllChargingStationsWithin(org.wololo.geojson.Geometry pGeoJson) {
        return null;
    }

}

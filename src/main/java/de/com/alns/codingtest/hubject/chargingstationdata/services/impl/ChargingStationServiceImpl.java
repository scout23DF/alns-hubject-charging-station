package de.com.alns.codingtest.hubject.chargingstationdata.services.impl;

import de.com.alns.codingtest.hubject.chargingstationdata.repositories.ChargingStationRepository;
import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.ChargingStationDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wololo.geojson.FeatureCollection;

import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
public class ChargingStationServiceImpl implements IChargingStationService {

    private ChargingStationRepository chargingStationRepository;

    @Autowired
    public ChargingStationServiceImpl(ChargingStationRepository pRepository) {
        this.chargingStationRepository = pRepository;
    }

    @Override
    public ChargingStationDTO saveChargingStation(ChargingStationDTO pChargingStationDTO) {
        return null;
    };

    @Override
    public ChargingStationDTO updateChargingStation(ChargingStationDTO pChargingStationDTO) {
        return null;
    }

    @Override
    public void deleteChargingStation(String pId) {

    }

    @Override
    public Boolean exists(String pId) {
        return null;
    }

    @Override
    public Optional<ChargingStationDTO> searchChargingStationById(String pId) {
        return Optional.empty();
    }

    @Override
    public Page<ChargingStationDTO> searchAllChargingStations(Pageable pPageable) {
        return null;
    }

    @Override
    public List<ChargingStationDTO> searchChargingStationsByZipCode(String pZipCodeNumber) {
        return null;
    }

    @Override
    public List<ChargingStationDTO> searchChargingStationsInCirclePerimeter(Double pLatitude, Double pLongitude, Double pRadius) {
        return null;
    }

}

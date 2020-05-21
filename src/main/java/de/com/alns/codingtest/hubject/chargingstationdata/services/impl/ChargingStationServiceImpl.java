package de.com.alns.codingtest.hubject.chargingstationdata.services.impl;

import com.vividsolutions.jts.geom.Polygon;
import de.com.alns.codingtest.hubject.chargingstationdata.domain.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.repositories.ChargingStationRepository;
import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.GeometryUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CommonsLog
public class ChargingStationServiceImpl implements IChargingStationService {

    private final ChargingStationRepository chargingStationRepository;

    @Autowired
    public ChargingStationServiceImpl(ChargingStationRepository pRepository) {
        this.chargingStationRepository = pRepository;
    }

    @Override
    public ChargingStation saveChargingStation(ChargingStation pChargingStation) {
        return chargingStationRepository.saveAndFlush(pChargingStation);
    }

    @Override
    public void deleteChargingStation(String pId) {
        chargingStationRepository.deleteById(pId);
    }

    @Override
    public Boolean exists(String pId) {
        return chargingStationRepository.existsById(pId);
    }

    @Override
    public Optional<ChargingStation> searchChargingStationById(String pId) {
        return chargingStationRepository.findById(pId);
    }

    @Override
    public Page<ChargingStation> searchAllChargingStations(Pageable pPageable) {
        return chargingStationRepository.findAll(pPageable);
    }

    @Override
    public List<ChargingStation> searchChargingStationsByZipCode(String pZipCodeNumber) {
        return chargingStationRepository.findByZipCodeNumber(pZipCodeNumber);
    }

    @Override
    public List<ChargingStation> searchChargingStationsInCirclePerimeter(PointLocationDTO pCircleCentralPoint, Double pRadius) {
        List<ChargingStation> resultList = null;
        Polygon circlePerimiterFilter;

        circlePerimiterFilter = GeometryUtils.buildCircleGeometryPerimiter(pCircleCentralPoint, pRadius);

        if (circlePerimiterFilter != null) {
            resultList = chargingStationRepository.findInPerimeter(circlePerimiterFilter);
        }

        return resultList;
    }

}

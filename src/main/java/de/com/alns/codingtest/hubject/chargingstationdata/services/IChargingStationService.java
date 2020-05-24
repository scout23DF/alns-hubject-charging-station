package de.com.alns.codingtest.hubject.chargingstationdata.services;

import de.com.alns.codingtest.hubject.chargingstationdata.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IChargingStationService {

    public ChargingStation saveChargingStation(ChargingStation pChargingStation);

    public List<ChargingStation> saveChargingStationsList(List<ChargingStation> pChargingStationsList);

    public void deleteChargingStation(String pId);

    public Boolean exists(String pId);

    public Optional<ChargingStation> searchChargingStationById(String pId);

    public Page<ChargingStation> searchAllChargingStations(Pageable pPageable);

    public List<ChargingStation> searchChargingStationsByZipCode(String pZipCodeNumber);

    public List<ChargingStation> searchChargingStationsInCirclePerimeter(PointLocationDTO pCircleCentralPoint, Double pRadius);

    public Long obtainTotalChargingStation();
}

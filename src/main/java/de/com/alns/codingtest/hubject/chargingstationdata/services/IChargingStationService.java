package de.com.alns.codingtest.hubject.chargingstationdata.services;

import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.ChargingStationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IChargingStationService {

    public ChargingStationDTO saveChargingStation(ChargingStationDTO pChargingStationDTO);

    public ChargingStationDTO updateChargingStation(ChargingStationDTO pChargingStationDTO);

    public void deleteChargingStation(String pId);

    public Boolean exists(String pId);

    public Optional<ChargingStationDTO> searchChargingStationById(String pId);

    public Page<ChargingStationDTO> searchAllChargingStations(Pageable pPageable);

    public List<ChargingStationDTO> searchChargingStationsByZipCode(String pZipCodeNumber);

    public List<ChargingStationDTO> searchChargingStationsInCirclePerimeter(Double pLatitude, Double pLongitude, Double pRadius);

}

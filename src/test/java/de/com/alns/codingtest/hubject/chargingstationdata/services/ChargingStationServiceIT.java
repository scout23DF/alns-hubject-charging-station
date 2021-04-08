package de.com.alns.codingtest.hubject.chargingstationdata.services;

import de.com.alns.codingtest.hubject.chargingstationdata.HubjectChargingStationDataApplication;
import de.com.alns.codingtest.hubject.chargingstationdata.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.HJDataGeneratorUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ChargingStation}.
 */
@SpringBootTest(classes = HubjectChargingStationDataApplication.class)
@Transactional
public class ChargingStationServiceIT {

    private static final PointLocationDTO DEFAULT_POINT_LOCATION_DTO = new PointLocationDTO(52.523435, 13.411442);


    @Autowired
    private IChargingStationService chargingStationService;

    private List<ChargingStation> chargingStationsTmpList;

    @Test
    @Transactional
    public void assertThatCreatedOneChargingStation() {
        ChargingStation chargingStationTmp;
        Optional<ChargingStation> optChargingStation;

        chargingStationsTmpList = HJDataGeneratorUtils.generateFakeChargingStationsInBerlin(1);

        chargingStationTmp = chargingStationService.saveChargingStation(chargingStationsTmpList.get(0));
        optChargingStation = chargingStationService.searchChargingStationById(chargingStationTmp.getMeaningfullId());

        assertThat(optChargingStation).isPresent();
    }

    @Test
    @Transactional
    public void assertThatSearchByPerimeter5KmWorks() {
        List<ChargingStation> chargingStationsFoundInPerimeterList;
        Optional<ChargingStation> optChargingStation;
        int qtyRecords = 100;

        chargingStationsTmpList = HJDataGeneratorUtils.generateFakeChargingStationsInBerlin(qtyRecords);

        chargingStationService.saveChargingStationsList(chargingStationsTmpList);

        chargingStationsFoundInPerimeterList = chargingStationService.searchChargingStationsInCirclePerimeter(DEFAULT_POINT_LOCATION_DTO, 5.0);

        assertThat(chargingStationsFoundInPerimeterList).size();
    }

}

package de.com.alns.codingtest.hubject.chargingstationdata;

import de.com.alns.codingtest.hubject.chargingstationdata.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.HJDataGeneratorUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class HubjectChargingStationDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubjectChargingStationDataApplication.class, args);
    }

    @Bean
    CommandLineRunner initializeDatabase(IChargingStationService pChargingStationService) {
        return (String... pArgsArray) -> {

            List<ChargingStation> chargingStationsCreatedList;
            Long qtyRecordsExistent;
            Integer qtyRecordsToInsert = 100;

            qtyRecordsExistent = pChargingStationService.obtainTotalChargingStation();

            if (qtyRecordsExistent <= 10) {
                if (pArgsArray != null && pArgsArray.length > 0 && StringUtils.isNumeric(pArgsArray[0])) {
                    qtyRecordsToInsert = Integer.parseInt(pArgsArray[0]);
                }
                chargingStationsCreatedList = HJDataGeneratorUtils.generateFakeChargingStationsInBerlin(qtyRecordsToInsert);
                pChargingStationService.saveChargingStationsList(chargingStationsCreatedList);
            }
        };
    }
}

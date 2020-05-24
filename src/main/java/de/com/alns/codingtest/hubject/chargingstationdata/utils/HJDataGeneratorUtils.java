package de.com.alns.codingtest.hubject.chargingstationdata.utils;

import de.com.alns.codingtest.hubject.chargingstationdata.models.ChargingStation;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class HJDataGeneratorUtils {

    private static final String DEFAULT_DESCRIPTION_CHARGING_STATION = "Station N# ";
    private static final Boolean DEFAULT_IS_ACTIVE = true;
    private static final Boolean DEFAULT_IS_AVAILABLE = true;


    public static List<ChargingStation> generateFakeChargingStationsInBerlin(Integer pQtyEntitiesToGenerate) {
        List<ChargingStation> chargingStationsTmpList;
        ChargingStation chargingStationTmp;
        String strMeaningfullIdTmp;

        chargingStationsTmpList = new ArrayList<>(pQtyEntitiesToGenerate);

        for (int i = 0; i < pQtyEntitiesToGenerate; i++) {
            chargingStationTmp = new ChargingStation();

            strMeaningfullIdTmp = "DE." + // RandomStringUtils.random(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ") + "." +
                                  "BER." +  // RandomStringUtils.random(3, "ABCDEFGHIJKLMNOPQRSTUVWXYZ") + "." +
                                  "D" + StringUtils.leftPad(HJRandomUtils.generateRandomIntBetweenRange(1, 101) + "", 3, "0") + "." +
                                  "S" + StringUtils.leftPad(HJRandomUtils.generateRandomIntBetweenRange(1, 1001) + "", 4, "0");

            chargingStationTmp.setMeaningfullId(strMeaningfullIdTmp);
            chargingStationTmp.setDescriptionChargingStation(DEFAULT_DESCRIPTION_CHARGING_STATION + " " + strMeaningfullIdTmp);
            chargingStationTmp.setIsActive(DEFAULT_IS_ACTIVE);
            chargingStationTmp.setIsAvailable(DEFAULT_IS_AVAILABLE);
            chargingStationTmp.setPercentuaEnergyLoaded(BigDecimal.valueOf(HJRandomUtils.generateRandomDoubleBetweenRange(10.0, 100.0)).setScale(2, RoundingMode.DOWN));
            chargingStationTmp.setZipCodeNumber("1" + HJRandomUtils.generateRandomIntBetweenRange(1000, 9999));
            chargingStationTmp.setGeoLocationPoint(HJGeometryUtils.convertDoublesToJtsGeometry(Double.valueOf("52.5" + HJRandomUtils.generateRandomIntBetweenRange(20000, 99999)),
                    Double.valueOf("13.4" + HJRandomUtils.generateRandomIntBetweenRange(15000, 99999))));

            chargingStationsTmpList.add(chargingStationTmp);
        }

        return chargingStationsTmpList;
    }

}

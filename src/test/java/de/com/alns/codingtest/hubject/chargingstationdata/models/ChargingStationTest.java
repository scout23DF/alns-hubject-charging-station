package de.com.alns.codingtest.hubject.chargingstationdata.models;

import de.com.alns.codingtest.hubject.chargingstationdata.utils.TestUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ChargingStationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingStation.class);

        ChargingStation chargingStation1 = new ChargingStation();
        chargingStation1.setMeaningfullId("DE.BER.D007.S0001");

        ChargingStation chargingStation2 = new ChargingStation();
        chargingStation2.setMeaningfullId(chargingStation1.getMeaningfullId());

        assertThat(chargingStation1).isEqualTo(chargingStation2);

        chargingStation2.setMeaningfullId("DE.BER.D007.S0002");

        assertThat(chargingStation1).isNotEqualTo(chargingStation2);

        chargingStation1.setMeaningfullId(null);

        assertThat(chargingStation1).isNotEqualTo(chargingStation2);
    }
}

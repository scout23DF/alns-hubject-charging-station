package de.com.alns.codingtest.hubject.chargingstationdata.config;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonForGeoTypesAsJSONConfig {

    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

}

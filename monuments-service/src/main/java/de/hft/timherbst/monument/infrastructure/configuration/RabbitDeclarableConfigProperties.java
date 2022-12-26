package de.hft.timherbst.monument.infrastructure.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "binding")
@Data
class RabbitDeclarableConfigProperties {

    private Monument monument;

    @Data
    static class Monument {
        private String exchange;
        private String queue;
        private String routingKey;
    }
}

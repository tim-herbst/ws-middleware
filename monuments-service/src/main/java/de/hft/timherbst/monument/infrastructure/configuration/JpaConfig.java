package de.hft.timherbst.monument.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"de.hft.timherbst.monument.infrastructure.adapter.out"})
@EntityScan(basePackages = {"de.hft.timherbst.monument.domain"})
class JpaConfig {
}

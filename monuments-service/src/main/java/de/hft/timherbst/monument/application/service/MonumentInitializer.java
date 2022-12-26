package de.hft.timherbst.monument.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort.MonumentDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class MonumentInitializer implements InitializingBean {

    private static final String RESOURCES_DIR = "classpath:datasources/*.json";

    private final PublishMonumentPort publishMonumentPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Initializing of monuments started");
        final Resource[] data = getData();
        final Set<MonumentDto[]> monuments =
                Arrays.stream(data).map(this::getMonumentsFromResources).collect(Collectors.toSet());
        monuments.forEach(
                dtos -> publishMonumentPort.publish(PublishMonumentPort.MonumentImportedPublishEvent.of(dtos)));
        log.info("Initializing of monuments finished");
    }

    @SneakyThrows
    private MonumentDto[] getMonumentsFromResources(final Resource resource) {
        return new ObjectMapper().readValue(resource.getFile(), MonumentDto[].class);
    }

    private Resource[] getData() throws IOException {
        ClassLoader loader = this.getClass().getClassLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);
        return resolver.getResources(RESOURCES_DIR);
    }
}

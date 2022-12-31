package de.hft.timherbst.monument.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort.MonumentDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class MonumentInitializer implements InitializingBean {

    private static final List<String> FILE_NAMES = List.of(
            "datasources/dithmarschen.json",
            "datasources/flensburg.json",
            "datasources/kiel.json",
            "datasources/lauenburg.json",
            "datasources/neumuenster.json",
            "datasources/nordfriesland.json",
            "datasources/ostholstein.json",
            "datasources/pinneberg.json",
            "datasources/rendsburg.json",
            "datasources/segeberg.json",
            "datasources/steinburg.json",
            "datasources/stomarn.json");

    private final PublishMonumentPort publishMonumentPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Initializing of monuments started");
        FILE_NAMES.forEach(name -> {
            final InputStream inputStream = getFileFromResourceAsStream(name);
            try {
                final MonumentDto[] monuments =
                        new ObjectMapper().readValue(inputStream.readAllBytes(), MonumentDto[].class);
                publishMonumentPort.publish(PublishMonumentPort.MonumentImportedPublishEvent.of(monuments));
            } catch (IOException e) {
                log.error("Error occurred while importing file {}", name, e);
                throw new RuntimeException(e);
            }
        });
        log.info("Initializing of monuments finished");
    }

    @SneakyThrows
    private MonumentDto[] getMonumentsFromResources(final Resource resource) {
        return new ObjectMapper().readValue(resource.getFile(), MonumentDto[].class);
    }

    @SneakyThrows
    private InputStream getFileFromResourceAsStream(final String name) {
        final ClassLoader loader = getClass().getClassLoader();
        final InputStream inputStream = loader.getResourceAsStream(name);

        if (inputStream == null) {
            throw new FileNotFoundException(String.format("Could not find file for name %s", name));
        } else {
            return inputStream;
        }
    }
}

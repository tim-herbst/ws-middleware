package de.hft.timherbst.monument.application.port.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.hft.timherbst.common.PublishEvent;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface PublishMonumentPort {

    /**
     * Publishes an importedevent containing a list of imported monuments.
     * @param event
     */
    void publish(MonumentImportedPublishEvent event);

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode(callSuper = false)
    class MonumentImportedPublishEvent extends PublishEvent {
        Collection<MonumentDto> monuments;

        public static MonumentImportedPublishEvent of(final MonumentDto... monuments) {
            return new MonumentImportedPublishEvent(Arrays.stream(monuments).collect(Collectors.toSet()));
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    class MonumentDto {

        @JsonProperty("Beschreibung")
        private String beschreibung;

        @JsonProperty("Bezeichnung")
        private String bezeichnung;

        @JsonProperty("Adresse-Lage")
        private String addressLage;

        @JsonProperty("Gemeinde")
        private String gemeinde;

        @JsonProperty("Kreis")
        private String kreis;

        @JsonProperty("Kulturdenkmaltyp")
        private String kulturDenkmalTyp;

        @JsonProperty("Objektnummer")
        private Long objektNummer;

        @JsonProperty("FotoURL")
        private String fotoURL;

        @JsonProperty("Begründung")
        private String[] begruendung;

        @JsonProperty("Schutzumfang")
        private String[] schutzUmfang;
    }
}

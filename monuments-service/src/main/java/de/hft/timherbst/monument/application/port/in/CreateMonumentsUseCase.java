package de.hft.timherbst.monument.application.port.in;

import de.hft.timherbst.common.SelfValidating;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface CreateMonumentsUseCase {

    /**
     * Imports all monuments of specific area.
     * @param command
     */
    void importMonuments(ImportMonumentsCommand command);

    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    class ImportMonumentsCommand extends SelfValidating<ImportMonumentsCommand> {

        @NotEmpty
        @Valid
        private final Collection<ImportedMonuments> monuments;

        public static ImportMonumentsCommand of(final Collection<ImportedMonuments> monuments) {
            ImportMonumentsCommand command = new ImportMonumentsCommand(monuments);
            command.validateSelf();
            return command;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    class ImportedMonuments {

        private String beschreibung;

        @NotBlank
        private String bezeichnung;

        private String addressLage;

        private String gemeinde;

        private String kreis;

        private String kulturDenkmalTyp;

        @NotNull
        private Long objektNummer;

        private String fotoURL;

        private String[] begruendung;

        private String[] schutzUmfang;
    }
}

package de.hft.timherbst.monument.application.port.in;

import de.hft.timherbst.common.SelfValidating;
import de.hft.timherbst.monument.domain.MonumentType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UpdateMonumentUseCase {

    CommandMapper MAPPER = Mappers.getMapper(CommandMapper.class);

    void updateExisting(UpdateMonumentCommand command);

    @Mapper
    interface CommandMapper {

        @Mapping(source = "description", target = "description")
        @Mapping(source = "type", target = "type")
        UpdateMonumentCommand toCommand(
                final UUID id,
                final MonumentType type,
                final String name,
                final String description,
                final String address,
                final String county,
                final String community,
                final String photoUrl,
                final Set<String> justifications,
                final Set<String> protectionScopes);
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    class UpdateMonumentCommand extends SelfValidating<UpdateMonumentCommand> {

        @NotNull
        private final UUID id;

        private final MonumentType type;

        @NotBlank
        private final String name;

        private final String description;

        private final String address;

        private final String county;

        private final String community;

        private final String photoUrl;

        private final Set<String> justifications;

        private final Set<String> protectionScopes;

        /**
         * override default lombok behavior to invoke selfValidating!
         */
        public static class UpdateMonumentCommandBuilder {
            public UpdateMonumentCommand build() {
                final UpdateMonumentCommand command = new UpdateMonumentCommand(
                        this.id,
                        this.type,
                        this.name,
                        this.description,
                        this.address,
                        this.county,
                        this.community,
                        this.photoUrl,
                        this.justifications,
                        this.protectionScopes);
                command.validateSelf();
                return command;
            }
        }
    }
}

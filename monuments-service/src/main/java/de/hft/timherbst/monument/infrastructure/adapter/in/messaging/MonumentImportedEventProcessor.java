package de.hft.timherbst.monument.infrastructure.adapter.in.messaging;

import de.hft.timherbst.common.EventProcessor;
import de.hft.timherbst.monument.application.port.in.CreateMonumentsUseCase;
import de.hft.timherbst.monument.application.port.in.CreateMonumentsUseCase.ImportMonumentsCommand;
import de.hft.timherbst.monument.application.port.in.CreateMonumentsUseCase.ImportedMonuments;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort.MonumentDto;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort.MonumentImportedPublishEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
class MonumentImportedEventProcessor implements EventProcessor<MonumentImportedPublishEvent> {
    private static final DtoMapper MAPPER = Mappers.getMapper(DtoMapper.class);

    private final CreateMonumentsUseCase createMonumentsUseCase;

    @Override
    public void process(MonumentImportedPublishEvent message) {
        final Collection<ImportedMonuments> importedMonuments = MAPPER.toCommand(message.getMonuments());
        createMonumentsUseCase.importMonuments(ImportMonumentsCommand.of(importedMonuments));
    }

    @Override
    public Class<MonumentImportedPublishEvent> getEventDataClass() {
        return MonumentImportedPublishEvent.class;
    }

    @Mapper
    interface DtoMapper {
        Collection<ImportedMonuments> toCommand(Collection<MonumentDto> dtos);

        ImportedMonuments toCommand(MonumentDto dto);
    }
}

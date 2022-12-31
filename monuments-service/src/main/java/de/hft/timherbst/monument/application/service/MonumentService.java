package de.hft.timherbst.monument.application.service;

import de.hft.timherbst.common.UseCase;
import de.hft.timherbst.monument.application.exceptions.ResourceNotFoundException;
import de.hft.timherbst.monument.application.port.in.CreateMonumentsUseCase;
import de.hft.timherbst.monument.application.port.in.DeleteMonumentUseCase;
import de.hft.timherbst.monument.application.port.in.MonumentQuery;
import de.hft.timherbst.monument.application.port.in.UpdateMonumentUseCase;
import de.hft.timherbst.monument.application.port.out.*;
import de.hft.timherbst.monument.domain.Justification;
import de.hft.timherbst.monument.domain.Monument;
import de.hft.timherbst.monument.domain.MonumentTableView;
import de.hft.timherbst.monument.domain.ScopeOfProtection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Service
@Transactional
@RequiredArgsConstructor
class MonumentService implements CreateMonumentsUseCase, MonumentQuery, UpdateMonumentUseCase, DeleteMonumentUseCase {

    private final LoadMonumentPort loadMonumentPort;
    private final CreateMonumentPort createMonumentPort;
    private final LoadJustificationPort loadJustificationPort;
    private final LoadScopeOfProtectionPort loadScopeOfProtectionPort;
    private final DeleteMonumentPort deleteMonumentPort;

    @Override
    public void importMonuments(final ImportMonumentsCommand command) {
        final Collection<Monument> internalMonuments =
                loadMonumentPort.findCreateableMonuments(Monument.MAPPER.toEntity(command.getMonuments()));
        if (CollectionUtils.isEmpty(internalMonuments)) {
            log.warn(
                    "No new monuments for county {} imported.",
                    command.getMonuments().stream()
                            .map(ImportedMonuments::getKreis)
                            .findFirst()
                            .orElse(null));
            return;
        }
        final Collection<Justification> internalJustifications = loadJustificationPort.getAllJustifications();
        final Collection<ScopeOfProtection> internalProtections = loadScopeOfProtectionPort.getAllProtections();

        final Map<String, Justification> justificationRegister = internalMonuments.stream()
                .map(Monument::getJustifications)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Justification::getName,
                        Function.identity(),
                        (j, j2) -> j)); // merge function necessary for duplicate names
        final Map<String, ScopeOfProtection> protectionRegister = internalMonuments.stream()
                .map(Monument::getScopeOfProtection)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        ScopeOfProtection::getProtection,
                        Function.identity(),
                        (j, j2) -> j)); // merge function necessary for duplicate names

        internalJustifications.stream()
                .filter(justification -> justificationRegister.containsKey(justification.getName()))
                .forEach(justification -> justificationRegister.replace(justification.getName(), justification));
        internalProtections.stream()
                .filter(protection -> protectionRegister.containsKey(protection.getProtection()))
                .forEach(protection -> protectionRegister.replace(protection.getProtection(), protection));

        for (final Monument monument : internalMonuments) {
            monument.handleJustifications(justificationRegister);
            monument.handleProtections(protectionRegister);

            createMonumentPort.save(monument);
            log.info("Monument saved {}", Map.of("name", monument.getName(), "id", monument.getId()));
        }
        log.info("import successful completed!");
    }

    @Override
    public Page<MonumentTableView> getAllMonumentsPaged(
            final Pageable pageable, final Specification<MonumentTableView> specification) {
        return loadMonumentPort.loadAllPaged(pageable, specification);
    }

    @Override
    public void updateExisting(final UpdateMonumentCommand command) {
        final Monument monument = loadMonumentPort
                .findById(command.getId())
                .orElseThrow(() -> new ResourceNotFoundException(Monument.class, command.getId()));

        monument.update(
                command.getName(),
                command.getDescription(),
                command.getAddress(),
                command.getCommunity(),
                command.getCounty(),
                command.getPhotoUrl(),
                command.getJustifications(),
                command.getProtectionScopes());
        log.info("Monument updated. {}", Map.of("monument", monument));
    }

    @Override
    public void delete(final UUID id) {
        final Monument monument =
                loadMonumentPort.findById(id).orElseThrow(() -> new ResourceNotFoundException(Monument.class, id));
        deleteMonumentPort.delete(monument);
        log.info("Monument successfully deleted {}", Map.of("monument", monument));
    }
}

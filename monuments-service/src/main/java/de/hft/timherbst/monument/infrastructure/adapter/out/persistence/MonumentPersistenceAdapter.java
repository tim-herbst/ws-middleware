package de.hft.timherbst.monument.infrastructure.adapter.out.persistence;

import de.hft.timherbst.common.PersistenceAdapter;
import de.hft.timherbst.monument.application.port.out.CreateMonumentPort;
import de.hft.timherbst.monument.application.port.out.LoadJustificationPort;
import de.hft.timherbst.monument.application.port.out.LoadMonumentPort;
import de.hft.timherbst.monument.application.port.out.LoadScopeOfProtectionPort;
import de.hft.timherbst.monument.domain.Justification;
import de.hft.timherbst.monument.domain.Monument;
import de.hft.timherbst.monument.domain.MonumentTableView;
import de.hft.timherbst.monument.domain.ScopeOfProtection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
class MonumentPersistenceAdapter
        implements LoadMonumentPort, CreateMonumentPort, LoadJustificationPort, LoadScopeOfProtectionPort {

    private final MonumentRepository monumentRepository;
    private final MonumentTableViewRepository monumentTableViewRepository;
    private final ScopeOfProtectionRepository scopeOfProtectionRepository;
    private final JustificationRepository justificationRepository;

    @Override
    public Collection<Monument> findCreateableMonuments(Collection<Monument> externalMonuments) {
        return externalMonuments.stream()
                .filter(monument ->
                        !monumentRepository.existsByNameAndObjectNumber(monument.getName(), monument.getObjectNumber()))
                .collect(Collectors.toSet());
    }

    @Override
    public Page<MonumentTableView> loadAllPaged(Pageable pageable, Specification<MonumentTableView> specification) {
        return monumentTableViewRepository.findAll(specification, pageable);
    }

    @Override
    public Monument save(final Monument monument) {
        return monumentRepository.save(monument);
    }

    @Override
    public Collection<Justification> getAllJustifications() {
        return justificationRepository.findAll();
    }

    @Override
    public Collection<ScopeOfProtection> getAllProtections() {
        return scopeOfProtectionRepository.findAll();
    }
}

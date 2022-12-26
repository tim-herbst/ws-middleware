package de.hft.timherbst.monument.infrastructure.adapter.out.persistence;

import de.hft.timherbst.monument.domain.MonumentTableView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MonumentTableViewRepository extends JpaRepository<MonumentTableView, UUID>, JpaSpecificationExecutor<MonumentTableView> {}

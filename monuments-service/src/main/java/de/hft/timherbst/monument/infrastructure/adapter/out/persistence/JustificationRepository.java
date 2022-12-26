package de.hft.timherbst.monument.infrastructure.adapter.out.persistence;

import de.hft.timherbst.monument.domain.Justification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JustificationRepository extends JpaRepository<Justification, UUID> {}

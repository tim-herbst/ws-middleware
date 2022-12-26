package de.hft.timherbst.monument.infrastructure.adapter.out.persistence;

import de.hft.timherbst.monument.domain.ScopeOfProtection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface ScopeOfProtectionRepository extends JpaRepository<ScopeOfProtection, UUID> {}

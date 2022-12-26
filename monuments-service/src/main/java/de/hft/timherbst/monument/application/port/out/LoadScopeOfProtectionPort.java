package de.hft.timherbst.monument.application.port.out;

import de.hft.timherbst.monument.domain.ScopeOfProtection;

import java.util.Collection;

public interface LoadScopeOfProtectionPort {

    /**
     * Fetches all protectionScopes available.
     * @return
     */
    Collection<ScopeOfProtection> getAllProtections();
}

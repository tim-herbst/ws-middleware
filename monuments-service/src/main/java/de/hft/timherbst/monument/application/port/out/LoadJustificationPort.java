package de.hft.timherbst.monument.application.port.out;

import de.hft.timherbst.monument.domain.Justification;

import java.util.Collection;
import java.util.Optional;

public interface LoadJustificationPort {

    /**
     * Fetches all available justifications.
     * @return
     */
    Collection<Justification> getAllJustifications();
}

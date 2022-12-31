package de.hft.timherbst.monument.application.port.in;

import de.hft.timherbst.monument.domain.Monument;

import java.util.UUID;

public interface DeleteMonumentUseCase {

    /**
     * Deletes an existing entity.
     * @param id of the entity to delete
     */
    void delete(UUID id);
}

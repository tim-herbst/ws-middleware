package de.hft.timherbst.monument.application.port.out;

import de.hft.timherbst.monument.domain.Monument;

public interface CreateMonumentPort {

    /**
     * Saves the corresponding entity to the database;
     * @param monument
     * @return
     */
    Monument save(Monument monument);
}

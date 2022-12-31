package de.hft.timherbst.monument.application.port.out;

import de.hft.timherbst.monument.domain.Monument;

import java.util.UUID;

public interface DeleteMonumentPort {

    void delete(Monument monument);
}

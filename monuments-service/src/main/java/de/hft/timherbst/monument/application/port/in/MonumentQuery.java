package de.hft.timherbst.monument.application.port.in;

import de.hft.timherbst.monument.domain.MonumentTableView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MonumentQuery {

    /**
     * TODO
     * @param pageable
     * @param specification
     * @return
     */
    Page<MonumentTableView> getAllMonumentsPaged(Pageable pageable, Specification<MonumentTableView> specification);
}

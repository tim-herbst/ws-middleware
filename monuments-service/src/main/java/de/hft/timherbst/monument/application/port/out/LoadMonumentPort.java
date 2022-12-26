package de.hft.timherbst.monument.application.port.out;

import de.hft.timherbst.monument.domain.Monument;
import de.hft.timherbst.monument.domain.MonumentTableView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface LoadMonumentPort {

    /**
     * TODO
     * @param externalMonuments
     * @return
     */
    Collection<Monument> findCreateableMonuments(Collection<Monument> externalMonuments);

    /**
     * TODO
     * @param pageable
     * @param specification
     * @return
     */
    Page<MonumentTableView> loadAllPaged(Pageable pageable, Specification<MonumentTableView> specification);
}

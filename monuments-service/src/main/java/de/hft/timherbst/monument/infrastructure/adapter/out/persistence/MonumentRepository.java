package de.hft.timherbst.monument.infrastructure.adapter.out.persistence;

import de.hft.timherbst.monument.domain.Monument;
import de.hft.timherbst.monument.domain.MonumentTableView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

interface MonumentRepository extends JpaRepository<Monument, UUID> {

    boolean existsByNameAndObjectNumber(String name, Long objectNumber);

    @Query(
            value =
                    "SELECT m.name, m.address, m.community, m.county, m.description, m.monument_type AS monumentType, m.objectNumber AS objectNumber, m.photo_url AS photoUrl, "
                            + "(SELECT string_agg(j.name, ',' order by j.name) AS values_agg FROM {h-schema}justification AS j "
                            + "INNER JOIN {h-schema}monument_justification AS mj on j.id = mj.justification_id AND mj.monument_id = m.id) AS justifications "
                            + "(SELECT string_agg(p.protection, ',' order by p.protection) AS values_agg FROM {h-schema}scope_of_protection AS p "
                            + "INNER JOIN {h-schema}monument_schope_of_protection AS msop ON p.id = msop.scope_of_protection_id AND msop.monument_id = m.id) AS scopeOfProtection"
                            + "FROM {h-schema}monument AS m",
            nativeQuery = true)
    Page<MonumentTableView> findAllMonumentsPaged(Pageable pageable, Specification<MonumentTableView> specification);
}

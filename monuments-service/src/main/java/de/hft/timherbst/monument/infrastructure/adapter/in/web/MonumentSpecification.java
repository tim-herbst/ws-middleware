package de.hft.timherbst.monument.infrastructure.adapter.in.web;

import de.hft.timherbst.monument.domain.MonumentTableView;
import de.hft.timherbst.monument.domain.MonumentType;
import de.hft.timherbst.monument.domain.Monument_;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
final class MonumentSpecification implements Specification<MonumentTableView> {

    private final Long objectNumber;
    private final String name;
    private final String description;
    private final String type;
    private final String address;
    private final String county;
    private final String community;
    private final String justifications;
    private final String protectionScopes;

    @Override
    public Predicate toPredicate(
            Root<MonumentTableView> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        final List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(type)) {
            addPredicateEquals(Monument_.TYPE, MonumentType.fromDescription(type), root, builder, predicates);
        }
        addPredicateEquals(Monument_.OBJECT_NUMBER, objectNumber, root, builder, predicates);

        addPredicateLike(Monument_.NAME, name, root, builder, predicates);
        addPredicateLike(Monument_.DESCRIPTION, description, root, builder, predicates);
        addPredicateLike(Monument_.ADDRESS, description, root, builder, predicates);
        addPredicateLike(Monument_.COUNTY, county, root, builder, predicates);
        addPredicateLike(Monument_.COMMUNITY, community, root, builder, predicates);
        addPredicateLike(Monument_.JUSTIFICATIONS, justifications, root, builder, predicates);
        addPredicateLike(Monument_.SCOPE_OF_PROTECTION, protectionScopes, root, builder, predicates);
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private void addPredicateEquals(
            final String field,
            final Object value,
            final Root<MonumentTableView> root,
            final CriteriaBuilder builder,
            final List<Predicate> predicates) {
        if (value != null) {
            predicates.add(builder.equal(root.get(field), value));
        }
    }

    private void addPredicateLike(
            final String field,
            final String value,
            final Root<MonumentTableView> root,
            final CriteriaBuilder builder,
            final List<Predicate> predicates) {
        if (value != null) {
            predicates.add(builder.like(builder.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
        }
    }
}

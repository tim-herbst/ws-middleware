package de.hft.timherbst.monument.domain;

import de.hft.timherbst.common.UuidEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SCOPE_OF_PROTECTION")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ScopeOfProtection extends UuidEntity {

    @Column(name = "PROTECTION")
    private String protection;

    public static ScopeOfProtection fromProtection(final String protection) {
        return new ScopeOfProtection(protection);
    }
}

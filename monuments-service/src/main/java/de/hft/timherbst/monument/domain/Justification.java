package de.hft.timherbst.monument.domain;

import de.hft.timherbst.common.UuidEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JUSTIFICATION")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Justification extends UuidEntity {

    @Column(name = "NAME")
    private String name;

    public static Justification fromName(final String name) {
        return new Justification(name);
    }
}

package de.hft.timherbst.monument.domain;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Immutable
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MonumentTableView {

    @Id
    private UUID id;

    private long objectNumber;

    @Enumerated(EnumType.STRING)
    private MonumentType type;

    private String name;
    private String description;
    private String address;
    private String county;
    private String community;
    private String photoUrl;
    private String justifications;
    private String scopeOfProtections;
}

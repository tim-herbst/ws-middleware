package de.hft.timherbst.monument.domain;

import de.hft.timherbst.common.UuidEntity;
import de.hft.timherbst.monument.application.port.in.CreateMonumentsUseCase;
import lombok.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "MONUMENT")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Monument extends UuidEntity {

    public static final MonumentMapper MAPPER = Mappers.getMapper(MonumentMapper.class);

    @Column(name = "OBJECT_NUMBER", nullable = false)
    @EqualsAndHashCode.Include
    private long objectNumber;

    @Column(name = "MONUMENT_TYPE", length = 33)
    @Enumerated(EnumType.STRING)
    private MonumentType type;

    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "COUNTY")
    private String county;

    @Column(name = "COMMUNITY")
    private String community;

    @Column(name = "PHOTO_URL")
    private String photoUrl;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "MONUMENT_JUSTIFICATION",
            joinColumns = @JoinColumn(name = "MONUMENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "JUSTIFICATION_ID", referencedColumnName = "ID"))
    @Builder.Default
    private Collection<Justification> justifications = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "MONUMENT_SCOPE_OF_PROTECTION",
            joinColumns = @JoinColumn(name = "MONUMENT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SCOPE_OF_PROTECTION_ID", referencedColumnName = "ID"))
    @Builder.Default
    private Collection<ScopeOfProtection> scopeOfProtection = new HashSet<>();

    public void handleJustifications(final Map<String, Justification> internalJustifications) {
        final Set<Justification> replaceableJustifications = internalJustifications.values().stream()
                .filter(j -> this.justifications.contains(j))
                .collect(Collectors.toSet());
        this.justifications.removeAll(replaceableJustifications);
        this.justifications.addAll(replaceableJustifications);
    }

    public void handleProtections(Map<String, ScopeOfProtection> internalProtections) {
        final Set<ScopeOfProtection> replaceableProtections = internalProtections.values().stream()
                .filter(p -> this.scopeOfProtection.contains(p))
                .collect(Collectors.toSet());
        this.scopeOfProtection.removeAll(replaceableProtections);
        this.scopeOfProtection.addAll(replaceableProtections);
    }

    @Mapper
    public interface MonumentMapper {
        Collection<Monument> toEntity(Collection<CreateMonumentsUseCase.ImportedMonuments> dtos);

        @Mapping(source = "kreis", target = "county")
        @Mapping(source = "gemeinde", target = "community")
        @Mapping(source = "addressLage", target = "address")
        @Mapping(source = "bezeichnung", target = "name")
        @Mapping(source = "beschreibung", target = "description")
        @Mapping(source = "objektNummer", target = "objectNumber")
        @Mapping(source = "fotoURL", target = "photoUrl")
        @Mapping(target = "type", expression = "java(mapType(dto.getKulturDenkmalTyp()))")
        @Mapping(target = "justifications", expression = "java(mapJustifications(dto.getBegruendung()))")
        @Mapping(target = "scopeOfProtection", expression = "java(mapProtections(dto.getSchutzUmfang()))")
        Monument toEntity(CreateMonumentsUseCase.ImportedMonuments dto);

        default MonumentType mapType(final String kulturDenkmalTyp) {
            return MonumentType.fromDescription(kulturDenkmalTyp);
        }

        default Collection<Justification> mapJustifications(final String... justifications) {
            if (Objects.isNull(justifications) || justifications.length == 0) {
                return Collections.emptySet();
            }
            return Arrays.stream(justifications).map(Justification::fromName).collect(Collectors.toSet());
        }

        default Collection<ScopeOfProtection> mapProtections(final String... protections) {
            if (Objects.isNull(protections) || protections.length == 0) {
                return Collections.emptySet();
            }
            return Arrays.stream(protections)
                    .map(ScopeOfProtection::fromProtection)
                    .collect(Collectors.toSet());
        }
    }
}

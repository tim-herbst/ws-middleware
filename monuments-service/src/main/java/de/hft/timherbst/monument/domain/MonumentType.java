package de.hft.timherbst.monument.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Slf4j
public enum MonumentType {
    MATERIAL_POPULATION("Sachgesamtheit"),
    STRUCTURAL_FACILITY("Bauliche Anlage"),
    MAJORITY_OF_STRUCTURAL_FACILITIES("Mehrheit von baulichen Anlagen"),
    MOVABLE_CULTURAL_MONUMENT("Bewegliches Kulturdenkmal"),
    GREEN_MONUMENT("Gründenkmal"),
    PART_OF_STRUCTURAL_FACILITY("Teil einer baulichen Anlage"),
    PROTECTION_ZONE("Schutzzone"),
    OTHER_MONUMENT("Sonstiges Denkmal");

    private static final Map<String, MonumentType> lookUp = Arrays.stream(MonumentType.values())
            .collect(Collectors.toMap(MonumentType::getDescription, Function.identity()));

    private final String description;

    public static MonumentType fromDescription(final String description) {
        return lookUp.get(description);
    }
}

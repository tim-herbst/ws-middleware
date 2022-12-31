package de.hft.timherbst.monument.infrastructure.adapter.in.web.adapter;

import de.hft.timherbst.monument.domain.MonumentType;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class UpdateMonumentRequest {
    UUID id;
    MonumentType type;
    String name;
    String description;
    String address;
    String county;
    String community;
    String photoUrl;
    Set<String> justifications;
    Set<String> scopeOfProtections;
}

package de.hft.timherbst.monument.infrastructure.adapter.in.web;

import de.hft.timherbst.common.WebAdapter;
import de.hft.timherbst.monument.application.port.in.DeleteMonumentUseCase;
import de.hft.timherbst.monument.application.port.in.MonumentQuery;
import de.hft.timherbst.monument.application.port.in.UpdateMonumentUseCase;
import de.hft.timherbst.monument.domain.MonumentTableView;
import de.hft.timherbst.monument.infrastructure.adapter.in.web.adapter.UpdateMonumentRequest;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@WebAdapter
@RestController
@RequestMapping(path = "/monuments", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class MonumentController {

    private final MonumentQuery monumentQuery;
    private final UpdateMonumentUseCase updateMonumentUseCase;
    private final DeleteMonumentUseCase deleteMonumentUseCase;

    @ApiResponse(code = 200, message = "Retrieves all Monuments paged", response = MonumentTableView.class)
    @GetMapping
    ResponseEntity<Page<MonumentTableView>> getAllMonumentsPaged(
            @NotNull final Pageable pageable,
            @RequestParam(required = false) final Long objectNumber,
            @RequestParam(required = false) final String type,
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String description,
            @RequestParam(required = false) final String address,
            @RequestParam(required = false) final String county,
            @RequestParam(required = false) final String community,
            @RequestParam(required = false) final String justifications,
            @RequestParam(required = false) final String scopeOfProtections) {
        final MonumentSpecification specification = MonumentSpecification.builder()
                .objectNumber(objectNumber)
                .type(type)
                .name(name)
                .description(description)
                .address(address)
                .county(county)
                .community(community)
                .justifications(justifications)
                .protectionScopes(scopeOfProtections)
                .build();

        return ResponseEntity.ok(monumentQuery.getAllMonumentsPaged(pageable, specification));
    }

    @ApiResponse(code = 204, message = "Updates an existing monument")
    @PatchMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateExistingMonument(@PathVariable final UUID id, @RequestBody final UpdateMonumentRequest request) {
        final UpdateMonumentUseCase.UpdateMonumentCommand command = UpdateMonumentUseCase.MAPPER.toCommand(
                id,
                request.getType(),
                request.getName(),
                request.getDescription(),
                request.getAddress(),
                request.getCounty(),
                request.getCommunity(),
                request.getPhotoUrl(),
                request.getJustifications(),
                request.getScopeOfProtections());
        updateMonumentUseCase.updateExisting(command);
    }

    @ApiResponse(code = 204, message = "Deletes an entity by given ID")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMonument(@PathVariable final UUID id) {
        deleteMonumentUseCase.delete(id);
    }
}

package de.hft.timherbst.monument.application.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public <T> ResourceNotFoundException(Class<T> clazz, UUID id) {
        super(String.format("Could not find (%s) for id:(%s)", clazz.getSimpleName(), id.toString()));
    }
}

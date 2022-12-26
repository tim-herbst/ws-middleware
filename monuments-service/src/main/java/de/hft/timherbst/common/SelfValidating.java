package de.hft.timherbst.common;

import lombok.SneakyThrows;
import org.springframework.util.CollectionUtils;

import javax.validation.*;
import java.util.Collection;
import java.util.Set;

public abstract class SelfValidating<T> {

    private final Validator validator;

    protected SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @SneakyThrows
    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (areViolationsPresent(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }

    private boolean areViolationsPresent(final Collection<ConstraintViolation<T>> violations) {
        return !CollectionUtils.isEmpty(violations);
    }
}

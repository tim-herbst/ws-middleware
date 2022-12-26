package de.hft.timherbst.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.io.Serializable;

public interface EventProcessor<T extends Serializable> {

    void process(T message);

    default T convert(String message) throws MessagingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return objectMapper.readValue(message, getEventDataClass());
        } catch (IOException e) {
            throw new MessagingException("Error occurred while converting json to object.", e);
        }
    }

    Class<T> getEventDataClass();
}

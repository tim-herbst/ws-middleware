package de.hft.timherbst.monument.infrastructure.adapter.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hft.timherbst.monument.application.port.out.PublishMonumentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class MonumentPublisher implements PublishMonumentPort {

    @Value("${binding.monument.exchange}")
    private String targetExchange;

    @Value("${binding.monument.routing-key}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(final MonumentImportedPublishEvent event) {
        try {
            rabbitTemplate.convertAndSend(targetExchange, routingKey, event.toJson());
        } catch (JsonProcessingException e) {
            log.error("Error occurred during event serialization {}", event, e);
        }
    }
}

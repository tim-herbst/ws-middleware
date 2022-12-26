package de.hft.timherbst.monument.infrastructure.adapter.in.messaging;

import de.hft.timherbst.monument.application.port.out.PublishMonumentPort.MonumentImportedPublishEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
class MonumentListener {

    private final MonumentImportedEventProcessor eventProcessor;

    @Value("${app.redeliveredMessageProcessTimeout}")
    protected long redeliveredMessageProcessTimeout;

    @RabbitListener(queues = "${binding.monument.queue}")
    protected void consumeEvent(String message, @Header(AmqpHeaders.REDELIVERED) boolean redelivered) {
        log.info("Consuming event started. {}", LocalDateTime.now());
        slowdownRedeliveredMessage(redelivered);
        final MonumentImportedPublishEvent convertedMessage = eventProcessor.convert(message);
        eventProcessor.process(convertedMessage);
        log.info("Consuming event finished. {}", LocalDateTime.now());
    }

    private void slowdownRedeliveredMessage(boolean redelivered) {
        if (redelivered) {
            try {
                Thread.sleep(redeliveredMessageProcessTimeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

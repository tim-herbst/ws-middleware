package de.hft.timherbst.monument.infrastructure.adapter.out.messaging;

import de.hft.timherbst.monument.application.port.out.PublishMonumentPort;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MonumentPublisherTest {

    @InjectMocks
    private MonumentPublisher publisher;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private static String randomString() {
        return RandomStringUtils.random(RandomUtils.nextInt(1, 100));
    }

    @Test
    @SneakyThrows
    void publishShouldSendEventAsJson() {
        // given
        final PublishMonumentPort.MonumentImportedPublishEvent event =
                PublishMonumentPort.MonumentImportedPublishEvent.of(PublishMonumentPort.MonumentDto.builder()
                        .addressLage(randomString())
                        .beschreibung(randomString())
                        .begruendung(new String[] {randomString(), randomString(), randomString()})
                        .fotoURL(randomString())
                        .kreis(randomString())
                        .bezeichnung(randomString())
                        .kulturDenkmalTyp(randomString())
                        .objektNummer(RandomUtils.nextLong())
                        .schutzUmfang(new String[] {randomString(), randomString()})
                        .gemeinde(randomString())
                        .build());

        // when
        publisher.publish(event);

        // then
        verify(rabbitTemplate).convertAndSend(any(), any(), eq(event.toJson()));
    }
}
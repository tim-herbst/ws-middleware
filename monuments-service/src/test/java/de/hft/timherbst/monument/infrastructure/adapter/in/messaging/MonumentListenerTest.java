package de.hft.timherbst.monument.infrastructure.adapter.in.messaging;

import de.hft.timherbst.monument.application.port.out.PublishMonumentPort;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MonumentListenerTest {

    @InjectMocks
    private MonumentListener listener;

    @Mock
    private MonumentImportedEventProcessor eventProcessor;

    private static String randomString() {
        return RandomStringUtils.random(RandomUtils.nextInt(1, 100));
    }

    @SneakyThrows
    @Test
    void consumeEventShouldConsumeMessage() {
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
        final String message = event.toJson();
        listener.consumeEvent(message, false);

        // then
        verify(eventProcessor).convert(message);
    }
}
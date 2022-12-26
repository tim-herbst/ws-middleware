package de.hft.timherbst.monument.infrastructure.adapter.out.persistence;

import de.hft.timherbst.monument.domain.Monument;
import de.hft.timherbst.monument.domain.MonumentTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonumentPersistenceAdapterTest {

    private static final MonumentTestFactory FACTORY = MonumentTestFactory.FACTORY;

    @InjectMocks
    private MonumentPersistenceAdapter persistenceAdapter;

    @Mock
    private MonumentRepository monumentRepository;

    @Mock
    private ScopeOfProtectionRepository scopeOfProtectionRepository;

    @Mock
    private JustificationRepository justificationRepository;

    @Test
    void findCreateableMonumentsShouldOnlyReturnCreatableEntities() {
        // given
        final Monument m1 = FACTORY.asEntity();
        final Monument m2 = FACTORY.asEntity();
        final List<Monument> monuments = List.of(m1, m2);

        // when
        persistenceAdapter.findCreateableMonuments(monuments);

        // then
        verify(monumentRepository).existsByNameAndObjectNumber(m1.getName(), m1.getObjectNumber());
        verify(monumentRepository).existsByNameAndObjectNumber(m2.getName(), m2.getObjectNumber());
    }

    @Test
    void findCreateableMonumentsShouldOnlyReturnCreatableEntitiesWhenTransient() {
        // given
        final Monument m1 = FACTORY.asEntity();
        final Monument m2 = FACTORY.asEntity();
        final Monument m3 = FACTORY.asEntity();
        final List<Monument> monuments = List.of(m1, m2, m3);

        // when
        when(monumentRepository.existsByNameAndObjectNumber(any(), any()))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);
        final Collection<Monument> createableMonuments = persistenceAdapter.findCreateableMonuments(monuments);

        // then
        assertThat(createableMonuments)
                .isNotEmpty()
                .hasSize(2)
                .containsExactlyInAnyOrder(m1, m2)
                .doesNotContain(m3);
    }

    @Test
    void saveShouldSaveEntity() {
        // given
        final Monument monument = FACTORY.asEntity();

        // when
        persistenceAdapter.save(monument);

        // then
        verify(monumentRepository).save(monument);
    }

    @Test
    void getAllJustificationsShouldRetrieveAllEntities() {
        // when
        persistenceAdapter.getAllJustifications();

        // then
        verify(justificationRepository).findAll();
    }

    @Test
    void getAllProtectionsShouldFindAllEntities() {
        // when
        persistenceAdapter.getAllProtections();

        // then
        verify(scopeOfProtectionRepository).findAll();
    }
}
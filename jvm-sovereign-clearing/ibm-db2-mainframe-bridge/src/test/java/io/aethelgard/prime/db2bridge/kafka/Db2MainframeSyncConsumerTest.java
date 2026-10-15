package io.aethelgard.prime.db2bridge.kafka;

import io.aethelgard.prime.contracts.SovereignSettlementEvent;
import io.aethelgard.prime.db2bridge.entity.SovereignMainframeVaultEntry;
import io.aethelgard.prime.db2bridge.repository.SovereignMainframeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Db2MainframeSyncConsumerTest {

    @Mock
    private SovereignMainframeRepository repository;

    @InjectMocks
    private Db2MainframeSyncConsumer consumer;

    @Test
    void shouldEnforceIdempotencyWhenSettlementAlreadyArchivedInDb2() {
        UUID id = UUID.randomUUID();
        SovereignSettlementEvent event = new SovereignSettlementEvent(
            id, "ECB_FRANKFURT_XX", "FED_NY_US", "USD", 500_000_00L, "SIG", "MERKLE_ROOT", Instant.now()
        );
        when(repository.findBySettlementUuid(id.toString()))
            .thenReturn(Optional.of(new SovereignMainframeVaultEntry()));

        consumer.archiveSettlementToDb2(event);

        verify(repository, never()).save(any());
    }
}

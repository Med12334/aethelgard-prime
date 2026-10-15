package io.aethelgard.prime.rtgs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.aethelgard.prime.contracts.SovereignSettlementEvent;
import io.aethelgard.prime.rtgs.entity.CbdcSettlementRecord;
import io.aethelgard.prime.rtgs.entity.OutboxEventEntity;
import io.aethelgard.prime.rtgs.repository.CbdcSettlementRepository;
import io.aethelgard.prime.rtgs.repository.OutboxEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CbdcRtgsClearingServiceTest {

    @Mock
    private CbdcSettlementRepository settlementRepository;

    @Mock
    private OutboxEventRepository outboxRepository;

    private CbdcRtgsClearingService clearingService;

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        clearingService = new CbdcRtgsClearingService(settlementRepository, outboxRepository, mapper);
    }

    @Test
    void shouldGenerateZkMerkleRootAndPersistTransactionalOutboxOnRtgsSettlement() throws Exception {
        SovereignSettlementEvent ev = clearingService.executeRtgsSettlement(
            "ECB_FRANKFURT_XX", "BIS_BASEL_CH", "EUR", 250_000_000_00L, "ML_DSA_87_SIG_0x99A"
        );

        assertNotNull(ev.zkMerkleProofRoot());
        assertEquals(64, ev.zkMerkleProofRoot().length());

        ArgumentCaptor<CbdcSettlementRecord> recordCaptor = ArgumentCaptor.forClass(CbdcSettlementRecord.class);
        verify(settlementRepository, times(1)).save(recordCaptor.capture());
        assertEquals("ECB_FRANKFURT_XX", recordCaptor.getValue().getCentralBankBic());

        ArgumentCaptor<OutboxEventEntity> outboxCaptor = ArgumentCaptor.forClass(OutboxEventEntity.class);
        verify(outboxRepository, times(1)).save(outboxCaptor.capture());
        assertEquals("sovereign-db2-mainframe-sync", outboxCaptor.getValue().getEventType());
    }
}

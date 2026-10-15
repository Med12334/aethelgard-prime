package io.aethelgard.prime.rtgs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.aethelgard.prime.contracts.SovereignSettlementEvent;
import io.aethelgard.prime.rtgs.entity.CbdcSettlementRecord;
import io.aethelgard.prime.rtgs.entity.OutboxEventEntity;
import io.aethelgard.prime.rtgs.repository.CbdcSettlementRepository;
import io.aethelgard.prime.rtgs.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class CbdcRtgsClearingService {

    private final CbdcSettlementRepository settlementRepository;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public CbdcRtgsClearingService(CbdcSettlementRepository settlementRepository,
                                   OutboxEventRepository outboxRepository,
                                   ObjectMapper objectMapper) {
        this.settlementRepository = settlementRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public SovereignSettlementEvent executeRtgsSettlement(
            String centralBankBic, String counterpartyBic, String currencyIso,
            long amountMinorUnits, String pqcDilithiumSig) throws Exception {

        UUID settlementId = UUID.randomUUID();
        String zkMerkleRoot = computeZkMerkleCommitment(settlementId, centralBankBic, counterpartyBic, amountMinorUnits);

        CbdcSettlementRecord record = new CbdcSettlementRecord(
            settlementId, centralBankBic, counterpartyBic, currencyIso,
            amountMinorUnits, pqcDilithiumSig, zkMerkleRoot
        );
        settlementRepository.save(record);

        SovereignSettlementEvent event = new SovereignSettlementEvent(
            settlementId, centralBankBic, counterpartyBic, currencyIso,
            amountMinorUnits, pqcDilithiumSig, zkMerkleRoot, Instant.now()
        );

        OutboxEventEntity outbox = new OutboxEventEntity(
            "CBDC_RTGS_SETTLEMENT",
            settlementId.toString(),
            "sovereign-db2-mainframe-sync",
            objectMapper.writeValueAsString(event)
        );
        outboxRepository.save(outbox);
        return event;
    }

    public String computeZkMerkleCommitment(UUID id, String bankA, String bankB, long amount) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String witness = "ZK_SNARK_WITNESS|" + id + "|" + bankA + "|" + bankB + "|" + amount;
        return HexFormat.of().formatHex(digest.digest(witness.getBytes(StandardCharsets.UTF_8)));
    }
}

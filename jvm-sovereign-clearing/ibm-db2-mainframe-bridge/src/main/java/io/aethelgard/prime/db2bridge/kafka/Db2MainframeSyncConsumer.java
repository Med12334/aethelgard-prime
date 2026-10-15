package io.aethelgard.prime.db2bridge.kafka;

import io.aethelgard.prime.contracts.SovereignSettlementEvent;
import io.aethelgard.prime.db2bridge.entity.SovereignMainframeVaultEntry;
import io.aethelgard.prime.db2bridge.repository.SovereignMainframeRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Db2MainframeSyncConsumer {

    private final SovereignMainframeRepository repository;

    public Db2MainframeSyncConsumer(SovereignMainframeRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "sovereign-db2-mainframe-sync", groupId = "db2-sovereign-rtgs-group")
    @Transactional
    public void archiveSettlementToDb2(SovereignSettlementEvent event) {
        String uuid = event.settlementId().toString();
        if (repository.findBySettlementUuid(uuid).isPresent()) {
            return;
        }
        SovereignMainframeVaultEntry entry = new SovereignMainframeVaultEntry(
            uuid,
            event.centralBankBic(),
            event.currencyIso(),
            event.amountMinorUnits(),
            event.zkMerkleProofRoot()
        );
        repository.save(entry);
    }
}

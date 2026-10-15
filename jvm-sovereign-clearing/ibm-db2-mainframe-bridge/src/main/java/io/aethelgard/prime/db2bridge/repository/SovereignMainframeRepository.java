package io.aethelgard.prime.db2bridge.repository;

import io.aethelgard.prime.db2bridge.entity.SovereignMainframeVaultEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SovereignMainframeRepository extends JpaRepository<SovereignMainframeVaultEntry, Long> {
    Optional<SovereignMainframeVaultEntry> findBySettlementUuid(String settlementUuid);
}

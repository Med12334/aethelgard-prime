package io.aethelgard.prime.rtgs.repository;

import io.aethelgard.prime.rtgs.entity.CbdcSettlementRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CbdcSettlementRepository extends JpaRepository<CbdcSettlementRecord, UUID> {}

package io.aethelgard.prime.contracts;

import java.time.Instant;
import java.util.UUID;

public record SovereignSettlementEvent(
    UUID settlementId,
    String centralBankBic,
    String counterpartyBic,
    String currencyIso,
    long amountMinorUnits,
    String pqcDilithiumSignature,
    String zkMerkleProofRoot,
    Instant settledAt
) {}

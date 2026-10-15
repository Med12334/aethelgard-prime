package io.aethelgard.prime.db2bridge.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "SOVEREIGN_RTGS_VAULT")
public class SovereignMainframeVaultEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAINFRAME_SEQ_ID")
    private Long mainframeSeqId;

    @Column(name = "SETTLEMENT_UUID", length = 36, nullable = false, unique = true)
    private String settlementUuid;

    @Column(name = "CENTRAL_BANK_BIC", length = 32, nullable = false)
    private String centralBankBic;

    @Column(name = "CURRENCY_ISO", length = 3, nullable = false)
    private String currencyIso;

    @Column(name = "AMOUNT_MINOR_UNITS", nullable = false)
    private long amountMinorUnits;

    @Column(name = "ZK_MERKLE_PROOF", length = 64, nullable = false)
    private String zkMerkleProof;

    @Column(name = "ARCHIVED_AT", nullable = false)
    private Instant archivedAt = Instant.now();

    public SovereignMainframeVaultEntry() {}

    public SovereignMainframeVaultEntry(String settlementUuid, String centralBankBic,
                                        String currencyIso, long amountMinorUnits, String zkMerkleProof) {
        this.settlementUuid = settlementUuid;
        this.centralBankBic = centralBankBic;
        this.currencyIso = currencyIso;
        this.amountMinorUnits = amountMinorUnits;
        this.zkMerkleProof = zkMerkleProof;
        this.archivedAt = Instant.now();
    }
}

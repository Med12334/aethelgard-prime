package io.aethelgard.prime.rtgs.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cbdc_rtgs_settlements")
public class CbdcSettlementRecord {

    @Id
    @Column(name = "settlement_id")
    private UUID settlementId;

    @Column(name = "central_bank_bic", length = 32, nullable = false)
    private String centralBankBic;

    @Column(name = "counterparty_bic", length = 32, nullable = false)
    private String counterpartyBic;

    @Column(name = "currency_iso", length = 3, nullable = false)
    private String currencyIso;

    @Column(name = "amount_minor_units", nullable = false)
    private long amountMinorUnits;

    @Column(name = "pqc_dilithium_sig", length = 128, nullable = false)
    private String pqcDilithiumSig;

    @Column(name = "zk_merkle_root", length = 64, nullable = false)
    private String zkMerkleRoot;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "settled_at", nullable = false)
    private Instant settledAt = Instant.now();

    public CbdcSettlementRecord() {}

    public CbdcSettlementRecord(UUID settlementId, String centralBankBic, String counterpartyBic,
                                String currencyIso, long amountMinorUnits,
                                String pqcDilithiumSig, String zkMerkleRoot) {
        this.settlementId = settlementId;
        this.centralBankBic = centralBankBic;
        this.counterpartyBic = counterpartyBic;
        this.currencyIso = currencyIso;
        this.amountMinorUnits = amountMinorUnits;
        this.pqcDilithiumSig = pqcDilithiumSig;
        this.zkMerkleRoot = zkMerkleRoot;
        this.settledAt = Instant.now();
    }

    public UUID getSettlementId() { return settlementId; }
    public String getCentralBankBic() { return centralBankBic; }
    public String getCounterpartyBic() { return counterpartyBic; }
    public String getCurrencyIso() { return currencyIso; }
    public long getAmountMinorUnits() { return amountMinorUnits; }
    public String getPqcDilithiumSig() { return pqcDilithiumSig; }
    public String getZkMerkleRoot() { return zkMerkleRoot; }
}

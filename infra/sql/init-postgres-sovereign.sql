CREATE EXTENSION IF NOT EXISTS vector;

-- Layer 16 & 21: Sovereign CBDC RTGS Settlement Accounts & Optimistic Locking
CREATE TABLE IF NOT EXISTS cbdc_rtgs_settlements (
    settlement_id UUID PRIMARY KEY,
    central_bank_bic VARCHAR(32) NOT NULL,
    counterparty_bic VARCHAR(32) NOT NULL,
    currency_iso CHAR(3) NOT NULL,
    amount_Minor_units BIGINT NOT NULL,
    pqc_dilithium_sig VARCHAR(128) NOT NULL,
    zk_merkle_root CHAR(64) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    settled_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Layer 18: Transactional Outbox Pattern Table
CREATE TABLE IF NOT EXISTS outbox_events (
    event_id UUID PRIMARY KEY,
    aggregate_type VARCHAR(64) NOT NULL,
    aggregate_id VARCHAR(64) NOT NULL,
    event_type VARCHAR(64) NOT NULL,
    payload TEXT NOT NULL,
    processed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cbdc_bic ON cbdc_rtgs_settlements(central_bank_bic);
CREATE INDEX idx_outbox_pending ON outbox_events(processed) WHERE processed = FALSE;

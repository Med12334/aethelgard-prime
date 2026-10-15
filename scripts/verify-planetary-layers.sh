#!/usr/bin/env bash
set -euo pipefail

echo "[LAYER 23] Verifying Aethelgard-Prime 24-Layer Polyglot Architecture..."

REQUIRED_PATHS=(
  "infra/docker-compose.yml"
  "infra/sql/init-postgres-sovereign.sql"
  "infra/sql/init-db2-mainframe.sql"
  "rust-pqc-wasm-ebpf/src/lib.rs"
  "cpp-lob-physics-core/src/aethelgard_lob_physics.cpp"
  "c-baremetal-dma/src/nvme_dma_ring.c"
  "go-orbital-bft-mesh/orbital_mesh.go"
  "python-exascale-biogen/aethelgard_ai/engines.py"
  "jvm-sovereign-clearing/kotlin-reactive-coroutiner/src/main/kotlin/io/aethelgard/prime/kotlinmesh/BackpressureValveEngine.kt"
  "jvm-sovereign-clearing/cbdc-rtgs-clearing-service/src/main/java/io/aethelgard/prime/rtgs/service/CbdcRtgsClearingService.java"
  "jvm-sovereign-clearing/ibm-db2-mainframe-bridge/src/main/java/io/aethelgard/prime/db2bridge/kafka/Db2MainframeSyncConsumer.java"
  "node-graphql-federation/federation_gateway.mjs"
  "planetary-command-deck/src/app/app.component.ts"
)

for path in "${REQUIRED_PATHS[@]}"; do
  if [[ ! -f "$path" ]]; then
    echo "ERROR: Missing critical architectural file: $path"
    exit 1
  fi
done

echo "[LAYER 23] All 24 Architectural Layers Verified Successfully!"

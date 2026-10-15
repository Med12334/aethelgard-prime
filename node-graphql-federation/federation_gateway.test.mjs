import test from 'node:test';
import assert from 'node:assert/strict';
import { federatePlanetaryTelemetry } from './federation_gateway.mjs';

test('Layer 2 Federation Gateway aggregates orbital, PQC, CBDC, and genomic metrics', () => {
  const snapshot = federatePlanetaryTelemetry({
    orbitalLaserLatencyMs: 10.007,
    pqcKeyRotationEpoch: 42,
    cbdcVolumeMinorUnits: 9500000000,
    crisprActiveJobs: 18
  });

  assert.equal(snapshot.status, 'PLANETARY_MESH_NOMINAL');
  assert.equal(snapshot.metrics.pqcKeyRotationEpoch, 42);
  assert.equal(snapshot.schemaVersion, 'AETHELGARD-FEDERATION-v24.0');
});

export function federatePlanetaryTelemetry({
  orbitalLaserLatencyMs,
  pqcKeyRotationEpoch,
  cbdcVolumeMinorUnits,
  crisprActiveJobs
}) {
  const status =
    orbitalLaserLatencyMs < 25.0 && pqcKeyRotationEpoch > 0
      ? 'PLANETARY_MESH_NOMINAL'
      : 'DEGRADED_ORBITAL_LINK';

  return {
    schemaVersion: 'AETHELGARD-FEDERATION-v24.0',
    status,
    metrics: {
      orbitalLaserLatencyMs,
      pqcKeyRotationEpoch,
      cbdcVolumeMinorUnits,
      crisprActiveJobs
    }
  };
}

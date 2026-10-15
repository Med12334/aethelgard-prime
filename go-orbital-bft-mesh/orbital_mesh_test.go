package main

import "testing"

func TestLayer6LaserLatencyCalculation(t *testing.T) {
hops := []SatelliteHop{
{SatelliteId: "LEO-SAT-101", DistanceKm: 1500.0},
{SatelliteId: "LEO-SAT-102", DistanceKm: 1500.0},
}
latency := ComputeLaserLatencyMs(hops)
if latency < 9.9 || latency > 10.1 {
t.Fatalf("Expected ~10.007ms laser propagation latency, got %f", latency)
}
}

func TestLayer7ByzantineQuorumValidation(t *testing.T) {
if !HasByzantineQuorum(7, 5) {
t.Fatal("Expected 5 votes out of 7 nodes (f=2, 2f+1=5) to achieve BFT quorum")
}
if HasByzantineQuorum(7, 4) {
t.Fatal("Expected 4 votes out of 7 nodes to fail BFT quorum")
}
}

func TestLayer8MicroVmBoot(t *testing.T) {
vm := SpawnMicroVm("vm-sovereign-01", 8, 4096)
if vm.Status != "MICROVM_ISOLATED_RUNNING" || vm.BootTimeMs >= 125 {
t.Fatalf("Unexpected micro-VM state: %+v", vm)
}
}

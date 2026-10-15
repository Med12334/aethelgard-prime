package io.aethelgard.prime.kotlinmesh

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BackpressureValveEngineTest {

    @Test
    fun `should trip circuit breaker to OPEN_SHEDDING_LOAD when inflight exceeds capacity`() {
        val engine = BackpressureValveEngine(maxInflightCapacity = 5000, criticalErrorThreshold = 5.0)
        val burst = TelemetryBurstSnapshot("EU-CENTRAL-ORBIT-01", inflightEvents = 6200, errorRatePercent = 1.2)
        assertEquals(MeshCircuitState.OPEN_SHEDDING_LOAD, engine.evaluateCircuitState(burst))
    }

    @Test
    fun `should remain CLOSED_NOMINAL under normal orbital telemetry load`() {
        val engine = BackpressureValveEngine(maxInflightCapacity = 5000, criticalErrorThreshold = 5.0)
        val nominal = TelemetryBurstSnapshot("US-EAST-GROUND-02", inflightEvents = 1400, errorRatePercent = 0.2)
        assertEquals(MeshCircuitState.CLOSED_NOMINAL, engine.evaluateCircuitState(nominal))
    }
}

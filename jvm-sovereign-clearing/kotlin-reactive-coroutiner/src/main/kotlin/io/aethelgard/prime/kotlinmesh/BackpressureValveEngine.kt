package io.aethelgard.prime.kotlinmesh

enum class MeshCircuitState {
    CLOSED_NOMINAL,
    HALF_OPEN_PROBING,
    OPEN_SHEDDING_LOAD
}

data class TelemetryBurstSnapshot(
    val regionCode: String,
    val inflightEvents: Int,
    val errorRatePercent: Double
)

class BackpressureValveEngine(
    private val maxInflightCapacity: Int = 10_000,
    private val criticalErrorThreshold: Double = 5.0
) {
    fun evaluateCircuitState(snapshot: TelemetryBurstSnapshot): MeshCircuitState {
        if (snapshot.errorRatePercent >= criticalErrorThreshold || snapshot.inflightEvents > maxInflightCapacity) {
            return MeshCircuitState.OPEN_SHEDDING_LOAD
        }
        if (snapshot.inflightEvents >= (maxInflightCapacity * 0.80).toInt()) {
            return MeshCircuitState.HALF_OPEN_PROBING
        }
        return MeshCircuitState.CLOSED_NOMINAL
    }
}

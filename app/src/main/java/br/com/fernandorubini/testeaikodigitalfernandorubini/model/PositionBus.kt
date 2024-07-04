package br.com.fernandorubini.testeaikofernandorubinifinal.model

import androidx.annotation.Keep

@Keep
data class PositionBus(
    val hr: String,
    val l: List<Linha>
)
@Keep
data class Linha(
    val c: String,
    val cl: Int,
    val sl: Int,
    val lt0: String,
    val lt1: String,
    val qv: Int,
    val vs: List<Veiculo>
)
@Keep
data class Veiculo(
    val p: Int,
    val a: Boolean,
    val ta: String,
    val py: Double,
    val px: Double
)


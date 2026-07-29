package com.enesakin.bmi.domain

import kotlin.math.round

enum class BmiCategory(val label: String) {
    UNDERWEIGHT("Zayıf"),
    NORMAL("Normal"),
    OVERWEIGHT("Fazla kilolu"),
    OBESE("Obez")
}

data class BmiResult(
    val value: Double,
    val category: BmiCategory
)

sealed interface BmiEvaluation {
    data class Success(val result: BmiResult) : BmiEvaluation
    data class Invalid(val message: String) : BmiEvaluation
}

object BmiCalculator {
    fun evaluate(weightText: String, heightText: String): BmiEvaluation {
        val weight = weightText.trim().replace(',', '.').toDoubleOrNull()
            ?: return BmiEvaluation.Invalid("Kilo alanına geçerli bir sayı gir.")
        val heightCm = heightText.trim().replace(',', '.').toDoubleOrNull()
            ?: return BmiEvaluation.Invalid("Boy alanına geçerli bir sayı gir.")

        if (!weight.isFinite() || weight !in 20.0..500.0) {
            return BmiEvaluation.Invalid("Kilo 20 ile 500 kg arasında olmalı.")
        }
        if (!heightCm.isFinite() || heightCm !in 80.0..250.0) {
            return BmiEvaluation.Invalid("Boy 80 ile 250 cm arasında olmalı.")
        }

        val heightMeters = heightCm / 100.0
        val raw = weight / (heightMeters * heightMeters)
        val rounded = round(raw * 10.0) / 10.0
        val category = when {
            raw < 18.5 -> BmiCategory.UNDERWEIGHT
            raw < 25.0 -> BmiCategory.NORMAL
            raw < 30.0 -> BmiCategory.OVERWEIGHT
            else -> BmiCategory.OBESE
        }

        return BmiEvaluation.Success(BmiResult(rounded, category))
    }
}

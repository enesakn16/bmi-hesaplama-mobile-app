package com.enesakin.bmi.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BmiCalculatorTest {
    @Test
    fun `calculates normal BMI with decimal comma`() {
        val evaluation = BmiCalculator.evaluate("75,5", "180")

        assertTrue(evaluation is BmiEvaluation.Success)
        val result = (evaluation as BmiEvaluation.Success).result
        assertEquals(23.3, result.value, 0.0)
        assertEquals(BmiCategory.NORMAL, result.category)
    }

    @Test
    fun `rejects invalid weight`() {
        val evaluation = BmiCalculator.evaluate("abc", "180")

        assertEquals(
            BmiEvaluation.Invalid("Kilo alanına geçerli bir sayı gir."),
            evaluation
        )
    }

    @Test
    fun `rejects unrealistic height`() {
        val evaluation = BmiCalculator.evaluate("75", "30")

        assertEquals(
            BmiEvaluation.Invalid("Boy 80 ile 250 cm arasında olmalı."),
            evaluation
        )
    }

    @Test
    fun `classifies obesity boundary`() {
        val evaluation = BmiCalculator.evaluate("97.2", "180") as BmiEvaluation.Success

        assertEquals(30.0, evaluation.result.value, 0.0)
        assertEquals(BmiCategory.OBESE, evaluation.result.category)
    }

    @Test
    fun `keeps underweight category when display value rounds to normal boundary`() {
        val evaluation = BmiCalculator.evaluate("59.81", "180") as BmiEvaluation.Success

        assertEquals(18.5, evaluation.result.value, 0.0)
        assertEquals(BmiCategory.UNDERWEIGHT, evaluation.result.category)
    }

    @Test
    fun `keeps normal category when display value rounds to overweight boundary`() {
        val evaluation = BmiCalculator.evaluate("80.84", "180") as BmiEvaluation.Success

        assertEquals(25.0, evaluation.result.value, 0.0)
        assertEquals(BmiCategory.NORMAL, evaluation.result.category)
    }

    @Test
    fun `keeps overweight category when display value rounds to obesity boundary`() {
        val evaluation = BmiCalculator.evaluate("97.04", "180") as BmiEvaluation.Success

        assertEquals(30.0, evaluation.result.value, 0.0)
        assertEquals(BmiCategory.OVERWEIGHT, evaluation.result.category)
    }
}

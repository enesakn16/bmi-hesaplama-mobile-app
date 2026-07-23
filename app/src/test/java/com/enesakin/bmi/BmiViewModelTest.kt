package com.enesakin.bmi

import org.junit.Assert.assertEquals
import org.junit.Test

class BmiViewModelTest {
    @Test
    fun `calculate updates feedback from domain result`() {
        val viewModel = BmiViewModel()

        viewModel.onWeightChanged("75,5")
        viewModel.onHeightChanged("180")
        viewModel.calculate()

        assertEquals("BMI: 23.3 · Normal", viewModel.uiState.feedback)
    }

    @Test
    fun `invalid input is exposed as field feedback`() {
        val viewModel = BmiViewModel()

        viewModel.onWeightChanged("abc")
        viewModel.onHeightChanged("180")
        viewModel.calculate()

        assertEquals("Kilo alanına geçerli bir sayı gir.", viewModel.uiState.feedback)
    }

    @Test
    fun `reset clears inputs and restores initial message`() {
        val viewModel = BmiViewModel()

        viewModel.onWeightChanged("90")
        viewModel.onHeightChanged("190")
        viewModel.calculate()
        viewModel.reset()

        assertEquals(BmiUiState(), viewModel.uiState)
    }
}

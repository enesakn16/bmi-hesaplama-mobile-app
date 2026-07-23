package com.enesakin.bmi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.enesakin.bmi.domain.BmiCalculator
import com.enesakin.bmi.domain.BmiEvaluation

data class BmiUiState(
    val weight: String = "",
    val height: String = "",
    val feedback: String = "Kilonu ve boyunu girerek hesaplamaya başla."
)

class BmiViewModel : ViewModel() {
    var uiState by mutableStateOf(BmiUiState())
        private set

    fun onWeightChanged(value: String) {
        uiState = uiState.copy(weight = value, feedback = INITIAL_MESSAGE)
    }

    fun onHeightChanged(value: String) {
        uiState = uiState.copy(height = value, feedback = INITIAL_MESSAGE)
    }

    fun calculate() {
        val feedback = when (val evaluation = BmiCalculator.evaluate(uiState.weight, uiState.height)) {
            is BmiEvaluation.Invalid -> evaluation.message
            is BmiEvaluation.Success ->
                "BMI: ${evaluation.result.value} · ${evaluation.result.category.label}"
        }
        uiState = uiState.copy(feedback = feedback)
    }

    fun reset() {
        uiState = BmiUiState()
    }

    private companion object {
        const val INITIAL_MESSAGE = "Kilonu ve boyunu girerek hesaplamaya başla."
    }
}

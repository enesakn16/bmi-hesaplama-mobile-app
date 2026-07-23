package com.enesakin.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BmiRoute()
                }
            }
        }
    }
}

@Composable
private fun BmiRoute(viewModel: BmiViewModel = viewModel()) {
    BmiScreen(
        state = viewModel.uiState,
        onWeightChanged = viewModel::onWeightChanged,
        onHeightChanged = viewModel::onHeightChanged,
        onCalculate = viewModel::calculate,
        onReset = viewModel::reset
    )
}

@Composable
private fun BmiScreen(
    state: BmiUiState,
    onWeightChanged: (String) -> Unit,
    onHeightChanged: (String) -> Unit,
    onCalculate: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("BMI Hesaplama", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(
            "Sonuç bilgilendirme amaçlıdır; tıbbi değerlendirme yerine geçmez.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = state.weight,
            onValueChange = onWeightChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Kilo (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = state.height,
            onValueChange = onHeightChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Boy (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onCalculate,
                modifier = Modifier.weight(1f)
            ) {
                Text("Hesapla")
            }
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.weight(1f)
            ) {
                Text("Temizle")
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(state.feedback, style = MaterialTheme.typography.titleMedium)
    }
}

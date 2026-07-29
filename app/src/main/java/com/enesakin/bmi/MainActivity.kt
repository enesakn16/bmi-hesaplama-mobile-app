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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

internal object BmiTestTags {
    const val TITLE = "bmi_title"
    const val WEIGHT_INPUT = "weight_input"
    const val HEIGHT_INPUT = "height_input"
    const val CALCULATE_BUTTON = "calculate_button"
    const val RESET_BUTTON = "reset_button"
    const val FEEDBACK = "bmi_feedback"
}

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
internal fun BmiScreen(
    state: BmiUiState,
    onWeightChanged: (String) -> Unit,
    onHeightChanged: (String) -> Unit,
    onCalculate: () -> Unit,
    onReset: () -> Unit
) {
    val heightFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "BMI Hesaplama",
            modifier = Modifier
                .testTag(BmiTestTags.TITLE)
                .semantics { heading() },
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Sonuç bilgilendirme amaçlıdır; tıbbi değerlendirme yerine geçmez.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = state.weight,
            onValueChange = onWeightChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(BmiTestTags.WEIGHT_INPUT),
            label = { Text("Kilo (kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { heightFocusRequester.requestFocus() }
            ),
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = state.height,
            onValueChange = onHeightChanged,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(heightFocusRequester)
                .testTag(BmiTestTags.HEIGHT_INPUT),
            label = { Text("Boy (cm)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onCalculate()
                }
            ),
            singleLine = true
        )
        Spacer(Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onCalculate,
                modifier = Modifier
                    .weight(1f)
                    .testTag(BmiTestTags.CALCULATE_BUTTON)
            ) {
                Text("Hesapla")
            }
            OutlinedButton(
                onClick = onReset,
                modifier = Modifier
                    .weight(1f)
                    .testTag(BmiTestTags.RESET_BUTTON)
            ) {
                Text("Temizle")
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = state.feedback,
            modifier = Modifier
                .testTag(BmiTestTags.FEEDBACK)
                .semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.titleMedium
        )
    }
}

package com.enesakin.bmi

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class BmiScreenAccessibilityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun screen_exposes_inputs_and_actions_with_stable_semantics() {
        composeRule.setContent {
            MaterialTheme {
                BmiScreen(
                    state = BmiUiState(),
                    onWeightChanged = {},
                    onHeightChanged = {},
                    onCalculate = {},
                    onReset = {}
                )
            }
        }

        composeRule.onNodeWithTag(BmiTestTags.WEIGHT_INPUT)
            .assertIsDisplayed()
            .assert(hasSetTextAction())
        composeRule.onNodeWithTag(BmiTestTags.HEIGHT_INPUT)
            .assertIsDisplayed()
            .assert(hasSetTextAction())
        composeRule.onNodeWithTag(BmiTestTags.CALCULATE_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
        composeRule.onNodeWithTag(BmiTestTags.RESET_BUTTON)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun title_is_heading_and_feedback_is_polite_live_region() {
        composeRule.setContent {
            MaterialTheme {
                BmiScreen(
                    state = BmiUiState(feedback = "BMI: 23.3 · Normal"),
                    onWeightChanged = {},
                    onHeightChanged = {},
                    onCalculate = {},
                    onReset = {}
                )
            }
        }

        composeRule.onNodeWithTag(BmiTestTags.TITLE)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Heading, Unit))
        composeRule.onNodeWithTag(BmiTestTags.FEEDBACK)
            .assert(
                SemanticsMatcher.expectValue(
                    SemanticsProperties.LiveRegion,
                    LiveRegionMode.Polite
                )
            )
    }
}

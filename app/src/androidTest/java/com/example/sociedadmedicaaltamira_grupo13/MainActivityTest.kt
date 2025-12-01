package com.example.sociedadmedicaaltamira_grupo13

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    /**
     * Test simple:
     * La pantalla inicial muestra el título de la app.
     * Ajusta el texto si tu título es distinto.
     */
    @Test
    fun pantallaInicial_muestraTituloApp() {
        // Cambia el texto si tu pantalla muestra otro título fijo
        composeRule.onNodeWithText("Sociedad Médica Altamira")
            .assertIsDisplayed()
    }
}

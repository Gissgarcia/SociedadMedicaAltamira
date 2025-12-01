package com.example.sociedadmedicaaltamira_grupo13

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.ReservaScreen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReservaScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    // 👉 El ViewModel se crea una sola vez, FUERA del composable
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel()
    }

    /**
     * Test 1:
     * Verifica que se muestren título, campos y botón de la pantalla de reserva.
     */
    @Test
    fun reservaScreen_muestraTituloCamposYBoton() {
        composeRule.setContent {
            val navController = rememberNavController()

            ReservaScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        // Título
        composeRule.onNodeWithText("Nueva Reserva").assertIsDisplayed()

        // Campos
        composeRule.onNodeWithText("Nombre").assertIsDisplayed()
        composeRule.onNodeWithText("Apellido").assertIsDisplayed()
        composeRule.onNodeWithText("Edad").assertIsDisplayed()
        composeRule.onNodeWithText("Correo").assertIsDisplayed()
        composeRule.onNodeWithText("Especialidad").assertIsDisplayed()

        // Botón
        composeRule.onNodeWithText("Confirmar reserva").assertIsDisplayed()
    }

    /**
     * Test 2:
     * Se puede escribir en el campo "Nombre" y el texto queda visible.
     */
    @Test
    fun reservaScreen_permiteIngresarNombre() {
        composeRule.setContent {
            val navController = rememberNavController()

            ReservaScreen(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }

        val nombre = "Paciente de prueba"

        // Escribimos en el TextField cuyo label es "Nombre"
        composeRule.onNodeWithText("Nombre")
            .performTextInput(nombre)

        // El texto ingresado aparece en la UI
        composeRule.onNodeWithText(nombre).assertIsDisplayed()
    }
}

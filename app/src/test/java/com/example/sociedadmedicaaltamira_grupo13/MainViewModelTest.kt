package com.example.sociedadmedicaaltamira_grupo13

import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Test

/**
 * Parte 7 – Tests de lógica/validaciones:
 * Pequeños tests unitarios sobre MainViewModel,
 * sin red ni corutinas, solo lógica de estado.
 */
class MainViewModelTest {

    /**
     * Test 1:
     * Al crear el MainViewModel, currentUser debe ser null
     * porque todavía no hay sesión iniciada.
     */
    @Test
    fun currentUser_esNull_alIniciar() {
        val viewModel = MainViewModel()

        assertNull(viewModel.currentUser.value)
    }

    /**
     * Test 2:
     * Cuando llamamos a setCurrentUser(user),
     * currentUser pasa a ser ese usuario.
     */
    @Test
    fun setCurrentUser_actualizaElUsuarioActual() {
        val viewModel = MainViewModel()
        val user = User(
            id = 1L,
            name = "Paciente Prueba",
            email = "paciente@test.cl",
            role = "CLIENT",
            token = "token123"
        )

        viewModel.setCurrentUser(user)

        assertEquals(user, viewModel.currentUser.value)
    }

    /**
     * Test 3:
     * Después de llamar a logout(), currentUser vuelve a null.
     */
    @Test
    fun logout_limpiaElUsuarioActual() {
        val viewModel = MainViewModel()
        val user = User(
            id = 1L,
            name = "Admin Prueba",
            email = "admin@test.cl",
            role = "ADMIN",
            token = "tokenABC"
        )

        // Primero iniciamos sesión
        viewModel.setCurrentUser(user)
        assertNotNull(viewModel.currentUser.value)

        // Luego cerramos sesión
        viewModel.logout()

        assertNull(viewModel.currentUser.value)
    }
}

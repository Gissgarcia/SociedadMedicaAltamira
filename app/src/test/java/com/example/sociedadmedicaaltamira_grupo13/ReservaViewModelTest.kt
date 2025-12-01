package com.example.sociedadmedicaaltamira_grupo13

import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse
import com.example.sociedadmedicaaltamira_grupo13.repository.ReservaRepository
import com.example.sociedadmedicaaltamira_grupo13.session.Session
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ReservaViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReservaViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        // Necesario para que viewModelScope (Dispatchers.Main) funcione en tests locales
        Dispatchers.setMain(testDispatcher)
        // Dejamos una Session limpia
        Session.userId = null
        Session.correo = null
        Session.token = null
        Session.nombreUsuario = null
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        Session.userId = null
        Session.correo = null
        Session.token = null
        Session.nombreUsuario = null
    }

    /**
     * Test 1 (guía 3.2.2 – ViewModel):
     * Cuando crearReserva es exitoso:
     *  - se llama al repositorio
     *  - el uiState muestra successMessage
     *  - isLoading queda en false
     *  - no hay error
     *  - se limpia el formulario
     */
    @Test
    fun crearReserva_exitoso_actualizaEstadoYLimpiaFormulario() = runTest {
        // Mock del repositorio
        val repository: ReservaRepository = mockk()

        // Lo que devolvería el backend (no es tan importante el contenido)
        val fakeResponse = ReservaResponse(
            id = 1L,
            nombre = "Juan",
            apellido = "Pérez",
            edad = 30,
            tipoDocumento = "RUT",
            numeroDocumento = "12345678-9",
            correo = "juan@test.cl",
            fechaReserva = "2025-12-01 10:00",
            especialidad = "Cardiología",
            idUsuario = 10L
        )

        // Configuramos MockK: cuando se llame a crearReserva, responder con fakeResponse
        coEvery { repository.crearReserva(any()) } returns fakeResponse

        // Simulamos que el usuario está logeado
        Session.userId = 10L

        // Creamos el ViewModel inyectando el mock
        val viewModel = ReservaViewModel(repository)

        // Llenamos el formulario como lo haría la UI
        viewModel.onNombreChange("Juan")
        viewModel.onApellidoChange("Pérez")
        viewModel.onEdadChange("30")
        viewModel.onTipoDocumentoChange("RUT")
        viewModel.onNumeroDocumentoChange("12345678-9")
        viewModel.onCorreoChange("juan@test.cl")
        viewModel.onFechaReservaChange("2025-12-01 10:00")
        viewModel.onEspecialidadChange("Cardiología")

        // Act: llamamos a crearReserva()
        viewModel.crearReserva()

        // Dejamos que corran las corrutinas del viewModelScope
        advanceUntilIdle()

        // Verificamos que se llamó al repositorio exactamente una vez
        coVerify(exactly = 1) { repository.crearReserva(any()) }

        // Revisamos el estado de la UI
        val state = viewModel.uiState.value
        assertEquals("Reserva creada correctamente", state.successMessage)
        assertFalse(state.isLoading)
        assertNull(state.error)

        // Y que el formulario se haya limpiado
        assertEquals("", viewModel.nombre)
        assertEquals("", viewModel.apellido)
        assertEquals("", viewModel.edad)
        assertEquals("", viewModel.tipoDocumento)
        assertEquals("", viewModel.numeroDocumento)
        assertEquals("", viewModel.fechaReserva)
        assertEquals("", viewModel.especialidad)
    }
    @Test
    fun cargarReservasUsuario_exitoso_actualizaListaEnUiState() = runTest {
        val repository: ReservaRepository = mockk()

        val fakeReservas = listOf(
            ReservaResponse(
                id = 1L,
                nombre = "Paciente 1",
                apellido = "Uno",
                edad = 25,
                tipoDocumento = "RUT",
                numeroDocumento = "11111111-1",
                correo = "paciente1@test.cl",
                fechaReserva = "2025-12-01 10:00",
                especialidad = "Dermatología",
                idUsuario = 10L
            ),
            ReservaResponse(
                id = 2L,
                nombre = "Paciente 2",
                apellido = "Dos",
                edad = 40,
                tipoDocumento = "RUT",
                numeroDocumento = "22222222-2",
                correo = "paciente2@test.cl",
                fechaReserva = "2025-12-02 11:00",
                especialidad = "Cardiología",
                idUsuario = 10L
            )
        )

        // El repositorio devuelve la lista falsa
        coEvery { repository.obtenerReservasPorUsuario() } returns fakeReservas

        // Simulamos que la sesión tiene correo
        Session.correo = "paciente1@test.cl"

        val viewModel = ReservaViewModel(repository)

        // Act
        viewModel.cargarReservasUsuario()
        advanceUntilIdle()

        // Se llamó al repositorio
        coVerify(exactly = 1) { repository.obtenerReservasPorUsuario() }

        // Estado final
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(fakeReservas, state.reservas)
    }
}

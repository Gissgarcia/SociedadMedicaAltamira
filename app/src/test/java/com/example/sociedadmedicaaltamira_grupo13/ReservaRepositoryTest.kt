package com.example.sociedadmedicaaltamira_grupo13

import com.example.sociedadmedicaaltamira_grupo13.data.remote.ReservaApiService
import com.example.sociedadmedicaaltamira_grupo13.data.remote.RetrofitClient
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaRequest
import com.example.sociedadmedicaaltamira_grupo13.data.remote.dto.ReservaResponse
import com.example.sociedadmedicaaltamira_grupo13.repository.ReservaRepository
import com.example.sociedadmedicaaltamira_grupo13.session.Session
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests unitarios del ReservaRepository.
 *
 * Aquí NO llamamos a la API real, sino que:
 *  - Mockeamos RetrofitClient.createReservaService() para que devuelva un ReservaApiService falso.
 *  - Mockeamos los métodos de ese ReservaApiService.
 *  - Probamos que el repository delega correctamente y devuelve los datos esperados.
 */
class ReservaRepositoryTest {

    @MockK
    lateinit var api: ReservaApiService

    private lateinit var repository: ReservaRepository

    @Before
    fun setup() {
        // Inicializa anotaciones @MockK
        MockKAnnotations.init(this, relaxUnitFun = true)

        // Mockeamos el objeto RetrofitClient
        mockkObject(RetrofitClient)

        // Cuando el repo llame a RetrofitClient.createReservaService(), devolvemos nuestro mock
        every { RetrofitClient.createReservaService(any()) } returns api
        every { RetrofitClient.createReservaService() } returns api

        // Ahora sí, creamos el repository real (usará el api mockeado)
        repository = ReservaRepository()

        // Limpiamos Session
        Session.correo = null
        Session.userId = null
        Session.token = null
        Session.nombreUsuario = null
    }

    @After
    fun tearDown() {
        // También dejamos la sesión limpia por si hay más tests
        Session.correo = null
        Session.userId = null
        Session.token = null
        Session.nombreUsuario = null
    }

    /**
     * Test 1:
     * crearReserva(request) debe:
     *  - llamar a api.crearReserva(request)
     *  - devolver el mismo ReservaResponse que retorna la API
     */
    @Test
    fun crearReserva_devuelveRespuestaDeLaApi() = runBlocking {
        val request = ReservaRequest(
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

        // Cuando el repository llame al api, devolvemos la respuesta falsa
        coEvery { api.crearReserva(request) } returns fakeResponse

        val result = repository.crearReserva(request)

        // Se llamó al método del api
        coVerify(exactly = 1) { api.crearReserva(request) }

        // Y el resultado es el mismo que devolvió la API
        assertEquals(fakeResponse, result)
    }

    /**
     * Test 2:
     * obtenerReservasPorUsuario() debe:
     *  - usar Session.correo
     *  - llamar a api.getReservasPorUsuario(correo)
     *  - devolver la lista entregada por la API
     */
    @Test
    fun obtenerReservasPorUsuario_usaCorreoDeSessionYDevuelveLista() = runBlocking {
        val correo = "paciente@test.cl"
        Session.correo = correo

        val fakeList = listOf(
            ReservaResponse(
                id = 1L,
                nombre = "Nombre 1",
                apellido = "Uno",
                edad = 25,
                tipoDocumento = "RUT",
                numeroDocumento = "11111111-1",
                correo = correo,
                fechaReserva = "2025-12-01 10:00",
                especialidad = "Dermatología",
                idUsuario = 10L
            ),
            ReservaResponse(
                id = 2L,
                nombre = "Nombre 2",
                apellido = "Dos",
                edad = 40,
                tipoDocumento = "RUT",
                numeroDocumento = "22222222-2",
                correo = correo,
                fechaReserva = "2025-12-02 11:00",
                especialidad = "Traumatología",
                idUsuario = 10L
            )
        )

        coEvery { api.getReservasPorUsuario(correo) } returns fakeList

        val result = repository.obtenerReservasPorUsuario()

        coVerify(exactly = 1) { api.getReservasPorUsuario(correo) }
        assertEquals(fakeList, result)
    }
}

package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.session.Session
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ReservaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    reservaViewModel: ReservaViewModel = viewModel()
) {
    val uiState by reservaViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Usuario actual (viene de MainViewModel, probablemente desde DataStore)
    val currentUser = mainViewModel.currentUser.value

    // Cada vez que tengamos currentUser, rellenamos Session y precargamos correo
    LaunchedEffect(currentUser) {
        currentUser?.let {
            // Precargar correo en el formulario
            reservaViewModel.onCorreoChange(it.email)

            // 🔥 Rellenar Session para que ReservaRepository tenga userId y token
            Session.userId = it.id
            Session.correo = it.email
            Session.nombreUsuario = it.name

            // Si tu modelo de usuario tiene token, ponlo aquí:
            // (ajusta el nombre del campo según tu User)
            Session.token = it.token   // <-- cambia "token" si tu propiedad tiene otro nombre
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Reserva") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = reservaViewModel.nombre,
                onValueChange = reservaViewModel::onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.apellido,
                onValueChange = reservaViewModel::onApellidoChange,
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.edad,
                onValueChange = reservaViewModel::onEdadChange,
                label = { Text("Edad") },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.tipoDocumento,
                onValueChange = reservaViewModel::onTipoDocumentoChange,
                label = { Text("Tipo documento (RUT, Pasaporte, etc)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.numeroDocumento,
                onValueChange = reservaViewModel::onNumeroDocumentoChange,
                label = { Text("Número documento") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.correo,
                onValueChange = reservaViewModel::onCorreoChange,
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.fechaReserva,
                onValueChange = reservaViewModel::onFechaReservaChange,
                label = { Text("Fecha y hora (ej: 2025-12-10 10:00)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = reservaViewModel.especialidad,
                onValueChange = reservaViewModel::onEspecialidadChange,
                label = { Text("Especialidad") },
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState.error != null) {
                Text(uiState.error ?: "", color = Color.Red)
            }

            Button(
                onClick = {
                    reservaViewModel.crearReserva()
                },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isLoading)
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                else
                    Text("Confirmar reserva")
            }
            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

        }
    }

    // Reacción a success / error
    LaunchedEffect(uiState.successMessage, uiState.error) {
        uiState.successMessage?.let { msg ->
            scope.launch {
                snackbarHostState.showSnackbar(msg)
                reservaViewModel.clearMessages()
                navController.navigate(Screen.ReservaList.route)
            }
        }
        uiState.error?.let { msg ->
            scope.launch {
                snackbarHostState.showSnackbar(msg)
                reservaViewModel.clearMessages()
            }
        }
    }
}
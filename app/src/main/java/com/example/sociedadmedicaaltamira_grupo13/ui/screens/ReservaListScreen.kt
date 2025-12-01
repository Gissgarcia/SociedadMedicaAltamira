package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ReservaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaListScreen(
    navController: NavController,
    viewModel: MainViewModel,
    reservaViewModel: ReservaViewModel = viewModel()
) {
    val uiState by reservaViewModel.uiState.collectAsState()
    val currentUser = viewModel.currentUser.value

    // Admin si el rol viene como "ADMIN" desde la API
    val esAdmin = currentUser?.role == "ADMIN"

    // Cargar reservas según el rol
    LaunchedEffect(currentUser, esAdmin) {
        currentUser ?: return@LaunchedEffect

        if (esAdmin) {
            reservaViewModel.cargarTodasReservas()
        } else {
            reservaViewModel.cargarReservasUsuario()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (esAdmin) "Todas las reservas" else "Mis reservas")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.reservas.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay reservas registradas.")
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(uiState.reservas) { r ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* aquí podrías ir a detalle */ },
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE3F2FD)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        "${r.nombre} ${r.apellido}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text("Especialidad: ${r.especialidad}")
                                    Text("Fecha: ${r.fechaReserva}")
                                    Text("Documento: ${r.tipoDocumento} ${r.numeroDocumento}")
                                    Text("Correo: ${r.correo}")

                                    if (esAdmin) {
                                        Spacer(Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Text(
                                                text = "Eliminar",
                                                color = Color.Red,
                                                modifier = Modifier
                                                    .clickable {
                                                        reservaViewModel.eliminarReserva(r.id)
                                                    }
                                                    .padding(4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (uiState.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = uiState.error ?: "",
                    color = Color.Red
                )
            }
        }
    }
}
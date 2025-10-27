@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ReservaListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReservaListScreen(
    navController: NavController,
    vm: ReservaListViewModel = viewModel()
) {
    val reservas by vm.reservas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis reservas") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { inner ->
        if (reservas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no tienes reservas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(inner)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reservas, key = { it.id }) { r ->
                    ElevatedCard {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "${r.nombre} ${r.apellido}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(6.dp))
                            Text("${r.docTipo}: ${r.docNumero}")
                            Text(r.email)

                            val fechaTexto = remember(r.fechaMillis) {
                                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    .format(Date(r.fechaMillis))
                            }
                            Text("Fecha: $fechaTexto")
                        }
                    }
                }
            }
        }
    }
}

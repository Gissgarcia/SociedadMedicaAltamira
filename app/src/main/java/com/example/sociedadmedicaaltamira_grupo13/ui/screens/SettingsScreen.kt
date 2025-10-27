@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ModoEspecialViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: ModoEspecialViewModel = viewModel() // reutilizamos el VM del modo especial
) {
    val s by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1) Preferencia persistente (DataStore)
            ListItem(
                headlineContent = { Text("Modo especial") },
                supportingContent = { Text("Activa funciones y estilos extra (se guarda en el dispositivo).") },
                trailingContent = {
                    Switch(
                        checked = s.enabled,
                        onCheckedChange = { checked ->
                            if (checked != s.enabled) viewModel.toggle()
                        }
                    )
                }
            )
            Divider()

            // 2) Acción de sesión / navegación
            Button(
                onClick = {

                    // por ahora, dejamos un regreso limpio a Home
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Volver al inicio") }
        }
    }
}

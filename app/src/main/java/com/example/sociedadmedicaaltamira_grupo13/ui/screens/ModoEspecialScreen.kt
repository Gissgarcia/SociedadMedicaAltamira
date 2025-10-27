@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ModoEspecialViewModel

@Composable
fun ModoEspecialScreen(
    navController: NavController,                 // 👈 agregado para navegar
    vm: ModoEspecialViewModel = viewModel()
) {
    val s by vm.state.collectAsState()

    var nota by rememberSaveable { mutableStateOf("") }

    val titulo by remember {
        derivedStateOf { if (s.enabled) "Modo Especial ACTIVADO" else "Modo Especial DESACTIVADO" }
    }

    val btnColor by animateColorAsState(
        targetValue = if (s.enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(400), label = "btnColor"
    )

    if (s.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(titulo, style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = nota,
            onValueChange = { nota = it },
            label = { Text("Nota (rememberSaveable)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { if (!s.isSaving) vm.toggle() },
            colors = ButtonDefaults.buttonColors(containerColor = btnColor),
            enabled = !s.isSaving
        ) {
            if (s.isSaving) {
                CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                Spacer(Modifier.width(8.dp))
            }
            Text(if (s.enabled) "Desactivar" else "Activar")
        }

        // Mensaje de éxito animado
        AnimatedVisibility(
            visible = s.message != null,
            enter = fadeIn(tween(250)),
            exit = fadeOut(tween(250))
        ) {
            AssistChip(onClick = { vm.clearMessage() }, label = { Text(s.message ?: "") })
        }

        Spacer(Modifier.height(8.dp))

        // 🆕 Opción B: volver SIEMPRE al inicio (limpia el back stack)
        TextButton(
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        ) { Text("Volver al inicio") }
    }
}

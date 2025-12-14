package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    vm: AuthViewModel = viewModel()
) {
    val AzulPrimario = Color(0xFF0D47A1)
    val s by vm.state.collectAsState()

    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar contraseña") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                "Ingresa tu email. El sistema generará un token (modo demo) para restablecer tu contraseña.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { vm.forgotPassword(email) },
                enabled = email.isNotBlank() && !s.isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
            ) {
                if (s.isLoading) {
                    CircularProgressIndicator(Modifier.size(18.dp), color = Color.White)
                } else {
                    Text("Enviar solicitud", color = Color.White)
                }
            }

            if (!s.message.isNullOrBlank()) {
                Text(s.message ?: "", color = AzulPrimario)
            }
        }
    }
}

package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.AuthViewModel
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: MainViewModel,                 // ← MainViewModel para setear usuario/navegación
    vm: AuthViewModel = viewModel()           // ← Tu VM de auth para manejar el formulario
) {
    var isLogin by remember { mutableStateOf(true) }
    val s by vm.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(if (isLogin) "Iniciar Sesión" else "Registrarse") }) }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Toggle simple Login/Registro
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledTonalButton(onClick = { isLogin = true }, enabled = !isLogin) { Text("Login") }
                FilledTonalButton(onClick = { isLogin = false }, enabled = isLogin) { Text("Registro") }
            }

            if (!isLogin) {
                OutlinedTextField(
                    value = s.name,
                    onValueChange = vm::updateName,
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            OutlinedTextField(
                value = s.email,
                onValueChange = vm::updateEmail,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = s.password,
                onValueChange = vm::updatePassword,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(visible = s.message != null) {
                Text(s.message ?: "", color = MaterialTheme.colorScheme.primary)
            }

            // Acción principal (login o registro)
            Button(
                onClick = {
                    // 1) Tu lógica (opcional) del AuthViewModel
                    if (isLogin) vm.login() else vm.register()

                    // 2) Construimos el usuario con los datos del formulario
                    val user = User(
                        name = if (isLogin) s.email.substringBefore("@") else s.name,
                        email = s.email,
                        passwordHash = s.password // si tienes hash real, cámbialo aquí
                    )

                    // 3) Guardamos en MainViewModel
                    viewModel.setCurrentUser(user)

                    // 4) Navegamos al Perfil
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                enabled = s.email.isNotBlank() && s.password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (s.isLoading) CircularProgressIndicator(Modifier.size(18.dp))
                else Text(if (isLogin) "Entrar" else "Crear cuenta")
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.End)
            ) { Text("Volver") }
        }
    }
}

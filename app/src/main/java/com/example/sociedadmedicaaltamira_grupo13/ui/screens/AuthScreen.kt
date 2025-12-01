package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.AuthViewModel
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: MainViewModel,
    vm: AuthViewModel = viewModel()
) {
    var isLogin by remember { mutableStateOf(true) }
    val s by vm.state.collectAsState()

    // Paleta local
    val AzulPrimario = Color(0xFF0D47A1)
    val AzulClaro = Color(0xFF1976D2)

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(s.message) {
        val msg = s.message ?: return@LaunchedEffect

        // Guardamos en variables locales para que Kotlin pueda hacer smart cast
        val userId = s.userId
        val role = s.role
        val token = s.token

        if (msg.contains("exitoso", ignoreCase = true) &&
            userId != null && role != null && token != null
        ) {
            // Construimos usuario REAL con datos del backend
            val user = User(
                id = userId,
                name = s.name,
                email = s.email,
                role = role,
                token = token
            )
            viewModel.setCurrentUser(user)

            // Mostrar snackbar de éxito y navegar
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "¡Bienvenido ${user.name}!",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        } else {
            // Mensaje de error u otro mensaje que no sea "exitoso"
            if (!msg.contains("exitoso", ignoreCase = true)) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = msg,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }

        vm.clearMessage()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isLogin) "Iniciar Sesión" else "Registrarse") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                SuccessSnackbar(data)
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Toggle estilizado Login / Registro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { isLogin = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLogin) AzulPrimario else Color.LightGray,
                        contentColor = if (isLogin) Color.White else Color.DarkGray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) { Text("Login") }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { isLogin = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isLogin) AzulClaro else Color.LightGray,
                        contentColor = if (!isLogin) Color.White else Color.DarkGray
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) { Text("Registro") }
            }

            // Campos
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
                Text(s.message ?: "", color = AzulPrimario)
            }

            // Botón principal
            Button(
                onClick = {
                    // ⬇️ AHORA solo disparamos la acción del ViewModel
                    if (isLogin) vm.login() else vm.register()
                },
                enabled = s.email.isNotBlank() && s.password.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
            ) {
                if (s.isLoading)
                    CircularProgressIndicator(Modifier.size(18.dp), color = Color.White)
                else
                    Text(if (isLogin) "Entrar" else "Crear cuenta", color = Color.White)
            }

            // Botón volver (animado)
            AnimatedBackButton(
                onClick = { navController.popBackStack() },
                color = AzulPrimario,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
private fun AnimatedBackButton(
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val scale by animateFloatAsState(if (pressed) 0.96f else 1f, label = "scale")
    val borderW by animateDpAsState(if (pressed) 2.dp else 1.dp, label = "bw")
    val tint by animateColorAsState(
        if (pressed) color.copy(alpha = 0.85f) else color,
        label = "tint"
    )

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interaction,
        modifier = modifier.graphicsLayer(scaleX = scale, scaleY = scale),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = tint),
        border = BorderStroke(borderW, tint),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Volver"
        )
        Spacer(Modifier.width(6.dp))
        Text("Volver")
    }
}

/* Snackbar de éxito con ícono */
@Composable
private fun SuccessSnackbar(data: SnackbarData) {
    val success = Color(0xFF2E7D32) // verde
    val onSuccess = Color.White

    Surface(
        color = success,
        contentColor = onSuccess,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 6.dp,
        shadowElevation = 6.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = onSuccess
                )
                Spacer(Modifier.width(10.dp))
                Text(data.visuals.message, style = MaterialTheme.typography.bodyMedium)
            }

            if (data.visuals.actionLabel != null) {
                TextButton(onClick = { data.performAction() }) {
                    Text(data.visuals.actionLabel!!, color = onSuccess)
                }
            }
        }
    }
}

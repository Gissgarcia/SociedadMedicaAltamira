package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.R
import com.example.sociedadmedicaaltamira_grupo13.model.User
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val AzulPrimario = Color(0xFF0D47A1)
private val GrisFondo = Color(0xFFF5F7FB)

@Composable
fun LoadingButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    minShowMs: Long = 1000L, // tiempo mínimo visible del spinner
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(12.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
    onClick: suspend () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            if (!isLoading && enabled) {
                scope.launch {
                    isLoading = true
                    val t0 = System.currentTimeMillis()

                    // Ejecuta tu acción real (navegar, guardar, etc.)
                    onClick()

                    // Asegura que el loader se vea al menos minShowMs
                    val elapsed = System.currentTimeMillis() - t0
                    val remaining = minShowMs - elapsed
                    if (remaining > 0) delay(remaining)

                    isLoading = false
                }
            }
        },
        enabled = enabled && !isLoading,
        colors = colors,
        shape = shape,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(18.dp)
            )
        } else {
            Text(text)
        }
    }
}

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel        // <- nombre estándar
) {
    val user: User? = viewModel.currentUser.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            text = "Perfil de Usuario",
            style = MaterialTheme.typography.titleLarge.copy(
                color = AzulPrimario,
                fontWeight = FontWeight.Bold
            )
        )

        if (user != null) {
            // Foto fija desde drawable
            Image(
                painter = painterResource(id = R.drawable.fotoperfil),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
            )

            Text(
                user.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF263238),
                textAlign = TextAlign.Center
            )
            Text(
                user.email,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Nombre: ${user.name}", fontSize = 16.sp)
                    Text("Correo: ${user.email}", fontSize = 16.sp)
                }
            }
        } else {
            Text("No hay usuario autenticado.", color = Color.Gray, fontSize = 16.sp)
        }

        // Botón para ir a Login / Registro
        LoadingButton(
            text = "Ir a Login / Registro",
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            delay(600)
            navController.navigate(Screen.Auth.route)
        }

        // 🔵 NUEVO BOTÓN: Mis reservas
        LoadingButton(
            text = "Mis reservas",
            enabled = user != null, // solo habilitado si hay usuario
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            delay(400)
            navController.navigate(Screen.ReservaList.route)
        }
    }
}
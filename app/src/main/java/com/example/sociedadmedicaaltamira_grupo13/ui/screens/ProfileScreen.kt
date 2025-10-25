package com.example.sociedadmedicaaltamira_grupo13.ui.screens


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ProfileState
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    profileVm: ProfileViewModel = viewModel()
) {
    val state by profileVm.state.collectAsState()
    val items = listOf(Screen.Home, Screen.Profile)
    var selectedItem by remember { mutableStateOf(1) }

    // 👇 Si estamos en Preview, NO crear launchers (causan Render problem)
    val isPreview = LocalInspectionMode.current

    val galleryLauncher =
        if (!isPreview) rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            profileVm.setFromGallery(uri)
        } else null

    val cameraLauncher =
        if (!isPreview) rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { ok: Boolean ->
            profileVm.setFromCamera(ok)
        } else null

    ProfileScreenContent(
        state = state,
        selectedItem = selectedItem,
        onSelectItem = { index ->
            selectedItem = index
            val screen = items[index]
            navController.navigate(screen.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        },
        onPickGallery = {
            if (!isPreview) {
                galleryLauncher?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        },
        onTakePhoto = {
            if (!isPreview) {
                val uri = profileVm.prepareCameraUri()
                cameraLauncher?.launch(uri)
            }
        },
        onClearMsg = { profileVm.clearMessage() },
        onBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    selectedItem: Int,
    onSelectItem: (Int) -> Unit,
    onPickGallery: () -> Unit,
    onTakePhoto: () -> Unit,
    onClearMsg: () -> Unit,
    onBack: () -> Unit
) {
    val items = listOf(Screen.Home, Screen.Profile)

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Perfil") }) },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { onSelectItem(index) },
                        icon = {
                            Icon(
                                imageVector = if (screen == Screen.Home) Icons.Filled.Home else Icons.Filled.Person,
                                contentDescription = screen.route
                            )
                        },
                        label = {
                            Text(
                                text = when (screen) {
                                    Screen.Home -> "Inicio"
                                    Screen.Profile -> "Perfil"
                                    else -> screen.route
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(shape = CircleShape, tonalElevation = 2.dp) {
                Image(
                    painter = rememberAsyncImagePainter(state.imageUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(140.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onPickGallery) { Text("Desde galería") }
                Button(onClick = onTakePhoto) { Text("Desde cámara") }
            }
            state.message?.let { AssistChip(onClick = onClearMsg, label = { Text(it) }) }
            Spacer(Modifier.weight(1f))
            OutlinedButton(onClick = onBack) { Text("Volver") }
        }
    }
}

/* ---------- PREVIEW SEGURO ---------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    SociedadMedicaAltamira_Grupo13Theme {
        // Usamos SOLO la UI pura con estado falso
        val fakeState = ProfileState(
            imageUri = null,
            tempPhotoUri = null,
            message = "Ejemplo de mensaje"
        )
        ProfileScreenContent(
            state = fakeState,
            selectedItem = 1,
            onSelectItem = {},
            onPickGallery = {},
            onTakePhoto = {},
            onClearMsg = {},
            onBack = {}
        )
    }
}
package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sociedadmedicaaltamira_grupo13.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import kotlinx.coroutines.launch
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Ítems del bottom bar (mismos Screen que ya usas)
    val items = listOf(Screen.Home, Screen.Profile)
    var selectedItem by remember { mutableStateOf(0) } // 0 = Home

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))
                NavigationDrawerItem(
                    label = { Text("Ir a perfil") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.navigateTo(Screen.Profile)
                        selectedItem = 1
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo de Sociedad Medica Altamira",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(horizontal = 0.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                // Usa tu patrón de navegación por ViewModel
                                viewModel.navigateTo(screen)
                            },
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.topappbanner),
                    contentDescription = "Logo App",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "¡Sociedad Médica Altamira!",
                    color = Color(0xFF0D47A1)
                )
                Button(onClick = { /* acción futura */ }) {
                    Text("Reserva")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}


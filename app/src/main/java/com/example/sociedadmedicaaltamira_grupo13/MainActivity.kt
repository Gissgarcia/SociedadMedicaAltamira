package com.example.sociedadmedicaaltamira_grupo13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sociedadmedicaaltamira_grupo13.navigation.NavigationEvent
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreenCompacta
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreenExpandida
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreenMedio
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.ProfileScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.SettingsScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import com.example.sociedadmedicaaltamira_grupo13.ui.utils.obtenerWindowSizeClass
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SociedadMedicaAltamira_Grupo13Theme {

                // ViewModel y NavController
                val viewModel: MainViewModel = viewModel()
                val navController = rememberNavController()

                // Escuchar eventos de navegación emitidos por el ViewModel
                LaunchedEffect(key1 = Unit) {
                    viewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(route = event.route.route) {
                                    event.popUpToRoute?.let {
                                        popUpTo(route = it.route) {
                                            inclusive = event.inclusive
                                        }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }

                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                // Layout base con NavHost
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Profile.route) {
                            ProfileScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Settings.route) {
                            SettingsScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SociedadMedicaAltamira_Grupo13Theme {
        Greeting("Sociedad Altamira")
    }

}
@Composable
fun HomeScreen2() {
    val windowSizeClass = obtenerWindowSizeClass()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta() // Llama a la versión compacta
        WindowWidthSizeClass.Medium -> HomeScreenMedio()  // Llama a la versión mediana
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida() // Llama a la versión expandida

    }
}

@Preview(name = "Compact", widthDp = 360, heightDp = 600)
@Composable
fun PreviewCompact(){
    HomeScreenCompacta()
}

@Preview(name = "Medium", widthDp = 360, heightDp = 800)
@Composable
fun PreviewMedium(){
    HomeScreenMedio()
}

@Preview(name = "Compact", widthDp = 360, heightDp = 900)
@Composable
fun PreviewExpanded(){
    HomeScreenExpandida()
}


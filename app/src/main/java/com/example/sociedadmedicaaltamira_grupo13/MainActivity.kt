package com.example.sociedadmedicaaltamira_grupo13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreen
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreenCompacta
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreenExpandida
import com.example.sociedadmedicaaltamira_grupo13.ui.screens.HomeScreenMedio
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import com.example.sociedadmedicaaltamira_grupo13.ui.utils.obtenerWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            SociedadMedicaAltamira_Grupo13Theme {
                HomeScreen2()

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
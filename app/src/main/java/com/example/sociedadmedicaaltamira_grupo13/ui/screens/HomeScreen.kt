package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.R
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel

/* ====== PALETA ====== */
private val AzulPrimario = Color(0xFF0D47A1)
private val AzulClaro = Color(0xFF1976D2)
private val GrisFondo = Color(0xFFF5F7FB)

/* =========================================================
   HomeScreen — Versión web-like (completa)
   Banner + Servicios + Equipo + Opiniones + Footer
========================================================= */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Sociedad Médica Altamira",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Bottom bar simple (Inicio / Perfil) tipo navbar
            NavigationBar {
                var selected by remember { mutableStateOf(0) }
                listOf(Screen.Home, Screen.Profile).forEachIndexed { idx, scr ->
                    NavigationBarItem(
                        selected = selected == idx,
                        onClick = {
                            selected = idx
                            viewModel.navigateTo(scr)
                        },
                        icon = {
                            Icon(
                                imageVector = if (scr == Screen.Home) Icons.Filled.Home else Icons.Filled.Person,
                                contentDescription = scr.route
                            )
                        },
                        label = { Text(if (scr == Screen.Home) "Inicio" else "Perfil") }
                    )
                }
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(GrisFondo)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Banner principal
            HeroBanner(
                title = "Rinología y Cirugía Plástica Facial",
                subtitle = "Atención de excelencia con tecnología de vanguardia",
                onReserva = { viewModel.navigateTo(Screen.Reserva) },
                onContacto = { viewModel.navigateTo(Screen.Auth) }, // o tu pantalla de contacto
            )

            // Sección Servicios
            SectionTitle("Servicios")
            ServicesRow(
                services = listOf(
                    ServiceItem("Rinología avanzada", "Diagnóstico y tratamiento nasal"),
                    ServiceItem("Cirugía plástica facial", "Funcional y estética"),
                    ServiceItem("Procedimientos estéticos", "Mínimamente invasivos")
                )
            )

            // Sección Equipo médico
            SectionTitle("Equipo Médico")
            DoctorsRow(
                doctors = listOf(
                    DoctorItem("Dra. María Rojas", "Rinología"),
                    DoctorItem("Dr. Vega", "Cirugía reconstructiva"),
                    DoctorItem("Dra. Valdés", "Medicina estética")
                )
            )

            // Sección Opiniones
            SectionTitle("Opiniones")
            Testimonials(
                quotes = listOf(
                    "Excelente atención, muy profesionales y amables.",
                    "Resultados impecables y seguimiento de calidad."
                )
            )

            // Footer


            Spacer(Modifier.height(16.dp))
        }
    }
}

/* =========================================================
   COMPONENTES REUTILIZABLES
========================================================= */

@Composable
private fun HeroBanner(
    title: String,
    subtitle: String,
    onReserva: () -> Unit,
    onContacto: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.topappbanner),
            contentDescription = "Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Degradado para legibilidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PrimaryButton(text = "Reserva tu hora", onClick = onReserva)
                Button(
                    onClick = onContacto,
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                ) {
                    Text("Iniciar sesion / Registrarse", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            color = AzulPrimario,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

/* -------- Servicios -------- */
private data class ServiceItem(val title: String, val subtitle: String)

@Composable
private fun ServicesRow(services: List<ServiceItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(services) { s ->
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .width(240.dp)
                    .heightIn(min = 120.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(s.title, fontWeight = FontWeight.SemiBold)
                    Text(
                        s.subtitle,
                        color = Color.Gray,
                        fontSize = 13.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

/* -------- Equipo médico -------- */
private data class DoctorItem(val name: String, val speciality: String)

@Composable
private fun DoctorsRow(doctors: List<DoctorItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(doctors) { d ->
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier.width(220.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar (usa imágenes reales si luego agregas drawables por médico)
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(AzulClaro.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = d.name.split(" ").first().first().toString(),
                            color = AzulPrimario,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(d.name, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 10.dp))
                    Text(d.speciality, color = Color.Gray, fontSize = 13.sp)
                }
            }
        }
    }
}

/* -------- Opiniones -------- */
@Composable
private fun Testimonials(quotes: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        quotes.forEach { q ->
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "“$q”",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF37474F)
                )
            }
        }
    }
}

/* -------- Footer -------- */


/* -------- Botón primario -------- */
@Composable
private fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(text)
    }
}

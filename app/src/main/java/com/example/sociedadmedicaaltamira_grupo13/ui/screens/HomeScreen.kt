@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sociedadmedicaaltamira_grupo13.R
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.MainViewModel
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch




/* ====== PALETA ====== */
private val AzulPrimario = Color(0xFF0D47A1)
private val AzulClaro = Color(0xFF1976D2)
private val GrisFondo = Color(0xFFF5F7FB)

/* =========================================================
   HomeScreen — Versión web-like (completa)
   Banner + Servicios + Equipo + Opiniones
========================================================= */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.White
                ),
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(350.dp)
                            .clip(CircleShape)
                    )
                }
            )
        }
        // 👇 SIN bottomBar aquí. La barra inferior vive en MainActivity.
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
                onReserva = { navController.navigate(Screen.Reserva.route) },   // ← antes usaba viewModel.navigateTo(...)
                onContacto = { navController.navigate(Screen.Auth.route) },
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
                    DoctorItem("Dra. De Vecchi", "Cirugía reconstructiva"),
                    DoctorItem("Dr. Perez", "Anestesiologo")
                )
            )

            // Sección Opiniones
            SectionTitle("Opiniones")
            Testimonials(
                quotes = listOf(
                    "Excelente atención, muy profesionales y amables.",
                    "Resultados impecables y seguimiento de calidad.",
                    "Encantada con los resultados."
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navController.navigate(Screen.ModoEspecial.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                ) {
                    Text("Ir a Modo Especial", color = Color.White)
                }
            }

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
    // Estados locales para simular la carga
    var loadingReserva by remember { mutableStateOf(false) }
    var loadingContacto by remember { mutableStateOf(false) }

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
            val scope = rememberCoroutineScope()
            // 🔹 Botones animados
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AnimatedLoadingButton(
                    text = if (loadingReserva) "Reservando..." else "Reserva tu hora",
                    isLoading = loadingReserva,
                    containerColor = AzulPrimario,
                    onClick = {
                        loadingReserva = true
                        scope.launch {
                            kotlinx.coroutines.delay(700)
                            loadingReserva = false
                            onReserva()
                        }
                    }
                )

                AnimatedLoadingButton(
                    text = if (loadingContacto) "Ingresando..." else "Iniciar sesión / Registrarse",
                    isLoading = loadingContacto,
                    containerColor = AzulPrimario,
                    onClick = {
                        loadingContacto = true
                        scope.launch {
                            kotlinx.coroutines.delay(700)
                            loadingContacto = false
                            onContacto()
                        }
                    }
                )
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
                    // Avatar (placeholder)
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

/* =========================================================
   PREVIEW — Sin ViewModel (seguro y funcional)
========================================================= */
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Vista previa HomeScreen"
)
@Composable
private fun HomeScreenPreview() {
    SociedadMedicaAltamira_Grupo13Theme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.White
                    ),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(350.dp)
                                .clip(CircleShape)
                        )
                    }
                )
            }
        ) { inner ->
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .background(GrisFondo)
                    .verticalScroll(rememberScrollState())
            ) {
                HeroBanner(
                    title = "Rinología y Cirugía Plástica Facial",
                    subtitle = "Atención de excelencia con tecnología de vanguardia",
                    onReserva = {},
                    onContacto = {}
                )
                SectionTitle("Servicios")
                ServicesRow(
                    services = listOf(
                        ServiceItem("Rinología avanzada", "Diagnóstico y tratamiento nasal"),
                        ServiceItem("Cirugía plástica facial", "Funcional y estética"),
                        ServiceItem("Procedimientos estéticos", "Mínimamente invasivos")
                    )
                )
            }
        }
    }
}

@Composable
private fun AnimatedCtaButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    // Escala y elevación animadas al presionar
    val scale by animateFloatAsState(targetValue = if (pressed) 0.97f else 1f, label = "scale")
    val elevation by animateDpAsState(targetValue = if (pressed) 0.dp else 6.dp, label = "elev")

    // Ligero “pulse” de color al presionar (opcional pero sutil)
    val animatedColor by animateColorAsState(
        targetValue = if (pressed) containerColor.copy(alpha = 0.92f) else containerColor,
        label = "color"
    )

    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = elevation),
        colors = ButtonDefaults.buttonColors(containerColor = animatedColor),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp),
        modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        Text(text, color = Color.White)
    }
}
@Composable
private fun AnimatedLoadingButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    // Animaciones de interacción
    val scale by animateFloatAsState(if (pressed && !isLoading) 0.96f else 1f, label = "scale")
    val elev by animateDpAsState(if (pressed || isLoading) 2.dp else 8.dp, label = "elev")
    val bg by animateColorAsState(
        if (pressed && !isLoading) containerColor.copy(alpha = 0.9f) else containerColor,
        label = "bg"
    )

    // Halo pulsante cuando está cargando (más visible)
    val pulse by rememberInfiniteTransition(label = "pulse")
        .animateFloat(
            initialValue = 0.95f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(650, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "halo"
        )

    val showHalo = isLoading

    Button(
        onClick = onClick,
        enabled = !isLoading,
        interactionSource = interactionSource,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = elev),
        colors = ButtonDefaults.buttonColors(containerColor = bg),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        modifier = modifier
            .graphicsLayer(
                scaleX = if (showHalo) pulse else scale,
                scaleY = if (showHalo) pulse else scale
            )
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                fadeIn(tween(150)) togetherWith fadeOut(tween(150))
            },
            label = "content"
        ) { loading ->
            if (loading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Cargando…", color = Color.White)
                }
            } else {
                Text(text, color = Color.White)
            }
        }
    }
}

package com.example.sociedadmedicaaltamira_grupo13.ui.screens
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.sociedadmedicaaltamira_grupo13.model.Reserva
import com.example.sociedadmedicaaltamira_grupo13.repository.AppMemory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReservaListScreen() {
    val reservas by AppMemory.reservas.collectAsState()
    val fmt = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }
    LazyColumn {
        items(reservas) { r: Reserva ->
            ListItem(
                headlineContent = { Text("${r.nombre} ${r.apellido} • ${fmt.format(Date(r.fechaMillis))}") },
                supportingContent = { Text("${r.docTipo}: ${r.docNumero} • ${r.email}") }
            )
            Divider()
        }
    }
}

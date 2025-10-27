@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sociedadmedicaaltamira_grupo13.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sociedadmedicaaltamira_grupo13.ui.theme.SociedadMedicaAltamira_Grupo13Theme
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ReservaFormState
import com.example.sociedadmedicaaltamira_grupo13.viewmodel.ReservaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen
import androidx.compose.material3.ExposedDropdownMenuBox

import androidx.compose.material3.ExposedDropdownMenuDefaults




@Composable
fun ReservaScreen(
    navController: NavController,
    vm: ReservaViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    var showDate by remember { mutableStateOf(false) }
    val isPreview = LocalInspectionMode.current

    ReservaScreenContent(
        state = state,
        onChange = { field, value -> vm.update(field, value) },
        onPickDate = { showDate = true },
        onSave = { vm.guardar() },
        onBack = { navController.popBackStack() },
        onViewList = { navController.navigate(com.example.sociedadmedicaaltamira_grupo13.navigation.Screen.Screen.ReservaList.route) }
    )

    // Evitar DatePicker en Preview
    if (showDate && !isPreview) {
        val pickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDate = false },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { vm.updateFecha(it) }
                    showDate = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDate = false }) { Text("Cancelar") } }
        ) {
            DatePicker(state = pickerState)
        }
    }
}

/** UI pura: apta para Preview */
@Composable
private fun ReservaScreenContent(
    state: ReservaFormState,
    onChange: (String, String) -> Unit,
    onPickDate: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onViewList: () -> Unit,            // 👈 NUEVO callback para navegar al listado
) {
    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Nueva Reserva") }) }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = { onChange("nombre", it) },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f),
                    isError = state.errors["nombre"] != null,
                    supportingText = { Text(state.errors["nombre"] ?: "") }
                )
                OutlinedTextField(
                    value = state.apellido,
                    onValueChange = { onChange("apellido", it) },
                    label = { Text("Apellido") },
                    modifier = Modifier.weight(1f),
                    isError = state.errors["apellido"] != null,
                    supportingText = { Text(state.errors["apellido"] ?: "") }
                )
            }

            OutlinedTextField(
                value = state.edad,
                onValueChange = { onChange("edad", it) },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.errors["edad"] != null,
                supportingText = { Text(state.errors["edad"] ?: "") }
            )

            // -------- Documento: RUT / PASAPORTE --------
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = state.docTipo,
                    onValueChange = {},                 // readOnly
                    readOnly = true,
                    label = { Text("Documento") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()                  // 👈 ancla correcta del menú
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("RUT") },
                        onClick = { onChange("docTipo", "RUT"); expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("PASAPORTE") },
                        onClick = { onChange("docTipo", "PASAPORTE"); expanded = false }
                    )
                }
            }
            // ------------------------------------------------

            OutlinedTextField(
                value = state.docNumero,
                onValueChange = { onChange("docNumero", it) },
                label = { Text(if (state.docTipo == "RUT") "RUT (12345678-5)" else "Pasaporte") },
                isError = state.errors["docNumero"] != null,
                supportingText = { Text(state.errors["docNumero"] ?: "") }
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = { onChange("email", it) },
                label = { Text("Correo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = state.errors["email"] != null,
                supportingText = { Text(state.errors["email"] ?: "") }
            )

            val fechaTexto = state.fechaMillis?.let {
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(it))
            } ?: "Seleccionar fecha"

            OutlinedButton(onClick = onPickDate) { Text(fechaTexto) }
            if (state.errors["fecha"] != null) {
                Text(state.errors["fecha"]!!, color = MaterialTheme.colorScheme.error)
            }

            state.message?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

            Button(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isSaving) CircularProgressIndicator(Modifier.size(18.dp))
                else Text("Confirmar reserva")
            }

            // 👇 NUEVO: botón para ver el listado de reservas
            Button(
                onClick = onViewList,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Text("Ver mis reservas", color = Color.White)
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.End)
            ) { Text("Volver") }
        }
    }
}

/* -------- PREVIEW -------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReservaScreenPreview() {
    val fake = ReservaFormState(
        nombre = "Ana", apellido = "Rojas", edad = "29",
        docTipo = "RUT", docNumero = "12345678-5", email = "ana@demo.cl",
        fechaMillis = System.currentTimeMillis(),
        message = "Vista de ejemplo"
    )
    SociedadMedicaAltamira_Grupo13Theme {
        ReservaScreenContent(
            state = fake,
            onChange = { _, _ -> },
            onPickDate = {},
            onSave = {},
            onBack = {},
            onViewList = {}   // preview sin navegación real
        )
    }
}

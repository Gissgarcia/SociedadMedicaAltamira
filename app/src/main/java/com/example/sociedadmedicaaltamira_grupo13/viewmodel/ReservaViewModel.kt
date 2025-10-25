package com.example.sociedadmedicaaltamira_grupo13.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociedadmedicaaltamira_grupo13.model.Reserva
import com.example.sociedadmedicaaltamira_grupo13.repository.ReservaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ReservaFormState(
    val nombre: String = "",
    val apellido: String = "",
    val edad: String = "",
    val docTipo: String = "RUT",
    val docNumero: String = "",
    val email: String = "",
    val fechaMillis: Long? = null,
    val errors: Map<String, String> = emptyMap(),
    val isSaving: Boolean = false,
    val savedOk: Boolean = false,
    val message: String? = null
)

class ReservaViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ReservaRepository()

    private val _state = MutableStateFlow(ReservaFormState())
    val state: StateFlow<ReservaFormState> = _state

    fun update(field: String, value: String) {
        val s = _state.value
        _state.value = when (field) {
            "nombre" -> s.copy(nombre = value)
            "apellido" -> s.copy(apellido = value)
            "edad" -> s.copy(edad = value.filter { it.isDigit() }.take(3))
            "docTipo" -> s.copy(docTipo = value)
            "docNumero" -> s.copy(docNumero = value.uppercase())
            "email" -> s.copy(email = value)
            else -> s
        }
    }
    fun updateFecha(millis: Long) { _state.value = _state.value.copy(fechaMillis = millis) }

    private fun validarRut(rut: String): Boolean {
        val clean = rut.replace(".", "").replace("-", "")
        if (clean.length !in 8..9) return false
        val body = clean.dropLast(1)
        val dv = clean.last().uppercaseChar()
        var sum = 0; var mul = 2
        for (i in body.reversed()) { sum += (i - '0') * mul; mul = if (mul == 7) 2 else mul + 1 }
        val res = 11 - (sum % 11)
        val dvCalc = when (res) { 11 -> '0'; 10 -> 'K'; else -> ('0' + res) }
        return dv == dvCalc
    }

    private fun validar(): Map<String,String> {
        val s = _state.value
        val e = mutableMapOf<String,String>()
        if (s.nombre.isBlank()) e["nombre"] = "Obligatorio"
        if (s.apellido.isBlank()) e["apellido"] = "Obligatorio"
        if (s.edad.toIntOrNull() == null || s.edad.toInt() !in 0..120) e["edad"] = "Edad inválida"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) e["email"] = "Email inválido"
        if (s.docTipo == "RUT") {
            if (!validarRut(s.docNumero)) e["docNumero"] = "RUT inválido (12345678-5)"
        } else if (s.docNumero.length < 5) e["docNumero"] = "Pasaporte inválido"
        if (s.fechaMillis == null) e["fecha"] = "Selecciona una fecha"
        return e
    }

    fun guardar() = viewModelScope.launch {
        val errs = validar()
        if (errs.isNotEmpty()) { _state.value = _state.value.copy(errors = errs, message = "Revisa los campos"); return@launch }
        val s = _state.value
        _state.value = s.copy(isSaving = true, errors = emptyMap(), message = null)
        repo.save(
            Reserva(
                nombre = s.nombre.trim(),
                apellido = s.apellido.trim(),
                edad = s.edad.toInt(),
                docTipo = s.docTipo,
                docNumero = s.docNumero.trim(),
                email = s.email.trim(),
                fechaMillis = s.fechaMillis!!
            )
        )
        _state.value = _state.value.copy(isSaving = false, savedOk = true, message = "Reserva creada")
    }
}

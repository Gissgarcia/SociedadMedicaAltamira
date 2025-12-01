package com.example.sociedadmedicaaltamira_grupo13.session


object Session {
    var token: String? = null        // Aquí se guardará el JWT del login
    var userId: Long? = null         // ID del usuario logueado
    var correo: String? = null       // Extra, si lo necesitas
    var nombreUsuario: String? = null
}

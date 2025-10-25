package com.example.sociedadmedicaaltamira_grupo13.repository
import com.example.sociedadmedicaaltamira_grupo13.model.User
import java.security.MessageDigest

class AuthRepository {
    private fun hash(p: String) =
        MessageDigest.getInstance("SHA-256").digest(p.toByteArray()).joinToString("") { "%02x".format(it) }

    suspend fun register(name: String, email: String, pass: String): Result<Long> {
        if (AppMemory.users.value.any { it.email.equals(email, ignoreCase = true) })
            return Result.failure(IllegalArgumentException("Email ya registrado"))
        val u = User(name = name.trim(), email = email.trim(), passwordHash = hash(pass))
        AppMemory.addUser(u)
        return Result.success(u.id)
    }

    suspend fun login(email: String, pass: String) : Result<User> {
        val h = hash(pass)
        val u = AppMemory.users.value.firstOrNull { it.email.equals(email, true) }
            ?: return Result.failure(IllegalArgumentException("Usuario no existe"))
        return if (u.passwordHash == h) Result.success(u)
        else Result.failure(IllegalArgumentException("Clave incorrecta"))
    }

    fun observeUsers() = AppMemory.users
}
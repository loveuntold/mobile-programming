package com.example.login_mvvm.model

import kotlinx.coroutines.delay

object AuthRepository {

    private const val DEMO_EMAIL = "demo@mail.com"
    private const val DEMO_PASSWORD = "password123"

    suspend fun login(email: String, password: String): Result<String> {
        delay(1500)
        return if (email.equals(DEMO_EMAIL, ignoreCase = true) && password == DEMO_PASSWORD) {
            Result.success("Demo User")
        } else {
            Result.failure(Exception("Email atau password salah"))
        }
    }
}

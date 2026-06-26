package com.example.aplikasi_registrasi_siswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.aplikasi_registrasi_siswa.ui.theme.Aplikasi_Registrasi_SiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aplikasi_Registrasi_SiswaTheme {
                Scaffold(containerColor = Color(0xFFF5F6FC)) { innerPadding ->
                    RegistrasiSiswaScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

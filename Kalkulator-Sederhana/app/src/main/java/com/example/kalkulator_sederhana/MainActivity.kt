package com.example.kalkulator_sederhana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalkulator_sederhana.ui.theme.KalkulatorSederhanaTheme
import com.example.kalkulator_sederhana.ui.theme.Pink40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorSederhanaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KalkulatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

private fun hitung(num1: Double, num2: Double, operator: String): String {
    val result = when (operator) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> {
            if (num2 != 0.0) num1 / num2
            else return "Error: Tidak bisa dibagi dengan nol!"
        }
        else -> return "Operator tidak valid!"
    }
    return "$num1 $operator $num2 = $result"
}

@Composable
fun KalkulatorScreen(modifier: Modifier = Modifier) {
    var angka1 by remember { mutableStateOf("") }
    var angka2 by remember { mutableStateOf("") }
    var hasil by remember { mutableStateOf("-") }

    val operators = listOf("+", "-", "*", "/")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Kalkulator Sederhana",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )

        OutlinedTextField(
            value = angka1,
            onValueChange = { angka1 = it },
            label = { Text("Masukkan angka pertama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = angka2,
            onValueChange = { angka2 = it },
            label = { Text("Masukkan angka kedua") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Pilih Operator:", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            operators.forEach { op ->
                Button(
                    onClick = {
                        val num1 = angka1.toDoubleOrNull()
                        val num2 = angka2.toDoubleOrNull()
                        hasil = if (num1 == null || num2 == null) {
                            "Masukkan angka yang valid!"
                        } else {
                            hitung(num1, num2, op)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pink40
                    ),
                    modifier = Modifier
                        .height(48.dp)
                ) {
                    Text(text = op, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = hasil,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KalkulatorScreenPreview() {
    KalkulatorSederhanaTheme {
        KalkulatorScreen()
    }
}

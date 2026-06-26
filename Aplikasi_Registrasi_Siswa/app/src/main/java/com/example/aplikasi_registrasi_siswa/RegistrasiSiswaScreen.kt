package com.example.aplikasi_registrasi_siswa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasi_registrasi_siswa.ui.theme.Aplikasi_Registrasi_SiswaTheme
import kotlinx.coroutines.launch

data class Siswa(
    val id: Long,
    val nama: String,
    val email: String
)

private val GradientStart = Color(0xFF8B5CF6)
private val GradientEnd = Color(0xFF5B3FE0)
private val PageBackground = Color(0xFFF5F6FC)
private val TitleColor = Color(0xFF1C1B2E)
private val SubtitleColor = Color(0xFF8A8894)

private val avatarPalette = listOf(
    Color(0xFFEDE7F6) to Color(0xFF6D4AFF),
    Color(0xFFFFF3CD) to Color(0xFFB8860B),
    Color(0xFFD7ECFF) to Color(0xFF1E6FBF),
    Color(0xFFE0F5E9) to Color(0xFF2E8B57),
    Color(0xFFFFE0E6) to Color(0xFFD6336C),
)

@Composable
fun RegistrasiSiswaScreen(modifier: Modifier = Modifier) {
    var siswaList by remember {
        mutableStateOf(
            listOf(
                Siswa(1L, "Rara Rusli", "rara.rusli@gmail.com"),
                Siswa(2L, "Dina Dungia", "dina.dungia@gmail.com"),
                Siswa(3L, "Budi Setiawan", "budi.setiawan@gmail.com"),
            )
        )
    }
    var nextId by remember { mutableLongStateOf(4L) }
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var editingId by remember { mutableStateOf<Long?>(null) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val isEditing = editingId != null
    val isFormValid = nama.isNotBlank() && email.isNotBlank() && email.contains("@")

    fun resetForm() {
        nama = ""
        email = ""
        editingId = null
    }

    fun saveSiswa() {
        if (!isFormValid) return
        val trimmedNama = nama.trim()
        val trimmedEmail = email.trim()
        siswaList = if (isEditing) {
            siswaList.map {
                if (it.id == editingId) it.copy(nama = trimmedNama, email = trimmedEmail) else it
            }
        } else {
            listOf(Siswa(nextId, trimmedNama, trimmedEmail)) + siswaList
        }
        if (!isEditing) nextId += 1
        resetForm()
    }

    fun startEdit(siswa: Siswa) {
        nama = siswa.nama
        email = siswa.email
        editingId = siswa.id
        coroutineScope.launch { listState.animateScrollToItem(0) }
    }

    fun deleteSiswa(id: Long) {
        siswaList = siswaList.filterNot { it.id == id }
        if (editingId == id) resetForm()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PageBackground)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HeaderSection() }
            item {
                FormCard(
                    nama = nama,
                    onNamaChange = { nama = it },
                    email = email,
                    onEmailChange = { email = it },
                    isEditing = isEditing,
                    isFormValid = isFormValid,
                    onSave = ::saveSiswa,
                    onCancelEdit = ::resetForm
                )
            }
            item { ListHeader(count = siswaList.size) }
            if (siswaList.isEmpty()) {
                item { EmptyState() }
            } else {
                itemsIndexed(siswaList, key = { _, siswa -> siswa.id }) { index, siswa ->
                    SiswaListItem(
                        siswa = siswa,
                        colorIndex = index,
                        isSelected = siswa.id == editingId,
                        onEdit = { startEdit(siswa) },
                        onDelete = { deleteSiswa(siswa.id) }
                    )
                }
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(GradientStart, GradientEnd))),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        Column {
            Text(
                text = "Registrasi Siswa",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TitleColor
            )
            Text(
                text = "Kelola data siswa dengan mudah",
                fontSize = 14.sp,
                color = SubtitleColor
            )
        }
    }
}

@Composable
private fun FormCard(
    nama: String,
    onNamaChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    isEditing: Boolean,
    isFormValid: Boolean,
    onSave: () -> Unit,
    onCancelEdit: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEditing) "Edit Data Siswa" else "Tambah Siswa Baru",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = GradientEnd
                )
                if (isEditing) {
                    TextButton(onClick = onCancelEdit) {
                        Text("Batal")
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            OutlinedTextField(
                value = nama,
                onValueChange = onNamaChange,
                label = { Text("Nama Lengkap") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GradientEnd,
                    focusedLabelColor = GradientEnd,
                    cursorColor = GradientEnd
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Alamat Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GradientEnd,
                    focusedLabelColor = GradientEnd,
                    cursorColor = GradientEnd
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(18.dp))
            SaveButton(
                isEditing = isEditing,
                isEnabled = isFormValid,
                onClick = onSave
            )
        }
    }
}

@Composable
private fun SaveButton(
    isEditing: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val background = if (isEnabled) {
        Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
    } else {
        Brush.horizontalGradient(listOf(Color(0xFFD8D5E3), Color(0xFFD8D5E3)))
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .clickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (isEditing) "Update Data" else "Simpan Data",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun ListHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Daftar Siswa",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TitleColor
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFEDE7F6))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "$count siswa",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6D4AFF)
            )
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.PersonOff,
            contentDescription = null,
            tint = Color(0xFFC4C1D0),
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Belum ada data siswa",
            color = SubtitleColor,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun SiswaListItem(
    siswa: Siswa,
    colorIndex: Int,
    isSelected: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val (bgColor, textColor) = avatarPalette[colorIndex % avatarPalette.size]
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 2.dp),
        border = if (isSelected) BorderStroke(1.5.dp, GradientEnd) else null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = siswa.nama.trim().firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = siswa.nama,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TitleColor
                )
                Text(
                    text = siswa.email,
                    fontSize = 13.sp,
                    color = SubtitleColor
                )
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = GradientEnd)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color(0xFFE05260))
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RegistrasiSiswaScreenPreview() {
    Aplikasi_Registrasi_SiswaTheme {
        RegistrasiSiswaScreen()
    }
}

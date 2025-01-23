package com.example.manprofinalpam.ui.view.proyek

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.proyek.InsertProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.InsertUiEvent
import com.example.manprofinalpam.ui.viewmodel.proyek.InsertUiState
import java.util.Calendar
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InsertProyekScreen(
    navigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InsertProyekVM = viewModel(factory = PenyediaVM.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Custom TopAppBar
        }
    ) { innerPadding ->
        ProyekFormBody(
            insertUiState = viewModel.uiEvent,
            onProyekValueChange = viewModel::updateInsertPryState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPry()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun ProyekFormBody(
    insertUiState: InsertUiState,
    onProyekValueChange: (InsertUiEvent) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        ProyekFormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onProyekValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun ProyekFormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Tanggal Mulai
    val tanggalMulaiPickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            // Format tanggal menjadi yyyy-MM-dd
            val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
            onValueChange(insertUiEvent.copy(tanggalMulai = formattedDate))
        },

        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Tanggal Berakhir
    val tanggalBerakhirPickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            // Format tanggalBerakhir menjadi yyyy-MM-dd
            val formattedEndDate = String.format("%04d-%02d-%02d", year, month + 1, day)
            onValueChange(insertUiEvent.copy(tanggalBerakhir = formattedEndDate))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProyek = it)) },
            label = { Text("Nama Proyek") },
            placeholder = { Text("Masukkan Nama Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProyek = it)) },
            label = { Text("Deskripsi Proyek") },
            placeholder = { Text("Masukkan Deskripsi Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalMulai,
            onValueChange = { },
            label = { Text("Tanggal Mulai") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { tanggalMulaiPickerDialog.show() },
            enabled = false, // Non-editable, hanya menggunakan DatePicker
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalBerakhir,
            onValueChange = { },
            label = { Text("Tanggal Berakhir") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { tanggalBerakhirPickerDialog.show() },
            enabled = false, // Non-editable, hanya menggunakan DatePicker
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.statusProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(statusProyek = it)) },
            label = { Text("Status Proyek") },
            placeholder = { Text("Masukkan Status Proyek") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(10.dp),
                color = Color.Red
            )
        }
        Divider(
            thickness = 5.dp,
            modifier = Modifier.padding(5.dp)
        )
    }
}
package com.example.manprofinalpam.ui.view.proyek

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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
import com.example.manprofinalpam.ui.viewmodel.proyek.DetailUiState
import kotlinx.coroutines.launch
import java.util.Locale

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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = colorResource(R.color.primary))
        ) {
            Spacer(
                Modifier
                    .height(16.dp)
                    .background(color = colorResource(id = R.color.primary))
            )
            CustomTopBar(title = "Tambah Proyek", onEditClick = {
            }, onBackClick = navigateBack, isEditEnabled = false)
            Spacer(
                Modifier
                    .height(16.dp)
                    .background(color = colorResource(R.color.primary))
            )

            Column(  modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(16.dp)){
                HorizontalDivider(
                    thickness = 5.dp,
                    modifier = Modifier.padding(horizontal = 128.dp)
                )
                Spacer(Modifier.padding(8.dp))
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
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan", fontWeight = FontWeight.Bold)
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
    var showDateRangePicker by remember { mutableStateOf(false) }

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

        // Tanggal Mulai dan Berakhir menggunakan DateRangePicker
        OutlinedTextField(
            value = "${insertUiEvent.tanggalMulai} - ${insertUiEvent.tanggalBerakhir}",
            onValueChange = {},
            label = { Text("Tanggal Mulai - Tanggal Berakhir") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDateRangePicker = true },
            enabled = false,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                disabledLabelColor = Color.Black,
                focusedTextColor = Color.Black, // Warna teks saat fokus
                unfocusedTextColor = Color.Black, // Warna teks saat tidak fokus
                disabledTextColor = Color.Black, // Warna teks saat disabled
                focusedIndicatorColor = Color.Blue, // Warna garis bawah saat fokus
                unfocusedIndicatorColor = Color.Gray, // Warna garis bawah saat tidak fokus
                disabledIndicatorColor = Color.Gray, // Warna garis bawah saat disabled
                focusedContainerColor = Color.Transparent, // Warna latar saat fokus
                unfocusedContainerColor = Color.Transparent, // Warna latar saat tidak fokus
                disabledContainerColor = Color.Transparent // Warna latar saat disabled
            )
        )

        if (showDateRangePicker) {
            DateRangePickerModal(
                onDateRangeSelected = { dateRange ->
                    val (startMillis, endMillis) = dateRange
                    val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    val startDate = startMillis?.let { dateFormat.format(it) } ?: ""
                    val endDate = endMillis?.let { dateFormat.format(it) } ?: ""

                    onValueChange(
                        insertUiEvent.copy(
                            tanggalMulai = startDate,
                            tanggalBerakhir = endDate
                        )
                    )
                    showDateRangePicker = false
                },
                onDismiss = { showDateRangePicker = false }
            )
        }

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
        HorizontalDivider(
            thickness = 5.dp,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Pilih Rentang Tanggal"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

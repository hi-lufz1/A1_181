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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.ui.customwidget.CustomOutlinedTextField
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
import com.example.manprofinalpam.ui.customwidget.DropDownWidget
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
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
    val optionsStatus = listOf("Aktif", "Dalam Progres", "Selesai")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("Nama Proyek")
        CustomOutlinedTextField(
            value = insertUiEvent.namaProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(namaProyek = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Deskripsi Proyek")
        CustomOutlinedTextField(
            value = insertUiEvent.deskripsiProyek,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiProyek = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Tanggal Mulai - Tanggal Berakhir")
        CustomOutlinedTextField(
            value = "${insertUiEvent.tanggalMulai} - ${insertUiEvent.tanggalBerakhir}",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDateRangePicker = true },
            enabled = false
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
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Status Proyek")
        optionsStatus.forEach { status ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(R.color.primary)
                ),
                    selected = insertUiEvent.statusProyek == status,
                    onClick = { onValueChange(insertUiEvent.copy(statusProyek = status)) }
                )
                Text(text = status)
            }
        }

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
        colors = DatePickerDefaults.colors(
            containerColor = Color.White, // Latar belakang dialog
            titleContentColor = Color.Black, // Warna judul
            headlineContentColor = Color.Black // Warna headline
        ),
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
                Text("OK", color = colorResource(R.color.primary))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel", color = colorResource(R.color.primary)
                )
            }
        }
    ) {

        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "  Pilih Rentang Tanggal"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(vertical = 16.dp),
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = colorResource(R.color.primary), // Warna background tanggal yang dipilih
                selectedDayContentColor = Color.White, // Warna teks tanggal yang dipilih
                todayDateBorderColor = colorResource(R.color.primary), // Border untuk tanggal hari ini
                dayInSelectionRangeContainerColor = Color(0x331085FE), // Warna background rentang yang dipilih
                dayInSelectionRangeContentColor = Color.Black // Warna teks dalam rentang yang dipilih
            )
        )

    }
}

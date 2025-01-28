package com.example.manprofinalpam.ui.view.tugas

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tugas.FormState
import com.example.manprofinalpam.ui.viewmodel.tugas.InsertTugasVM
import com.example.manprofinalpam.ui.viewmodel.tugas.InsertUiEvent
import com.example.manprofinalpam.ui.viewmodel.tugas.InsertUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InsertTugasScreen(
    navigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InsertTugasVM = viewModel(factory = PenyediaVM.Factory)
) { val snackbarHostState = remember { SnackbarHostState() } // State untuk Snackbar
    val coroutineScope = rememberCoroutineScope()
    val teamData by viewModel.timList.collectAsState()
    val formState = viewModel.formState // Observe state dari ViewModel

    Scaffold(
        modifier = modifier,snackbarHost = {  SnackbarHost(
            hostState = snackbarHostState
        ) { snackbarData ->
            Snackbar(
                shape = RoundedCornerShape(8.dp),
                snackbarData = snackbarData,
                containerColor = colorResource(id = R.color.primary), // Warna latar snackbar
                contentColor = Color.White, // Warna teks
                actionColor = Color.Yellow // Warna tombol aksi (jika ada)
            )
        }
        },
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
            CustomTopBar(title = "Tambah Tugas", onEditClick = {
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
                TugasFormBody(
                    insertUiState = viewModel.uiEvent,
                    onTugasValueChange = viewModel::updateInsertTugasState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertTugas()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    teamData = teamData
                )
            }
        }
    }
    // LaunchedEffect untuk menangani perubahan FormState
    LaunchedEffect(formState) {
        when (formState) {
            is FormState.Success -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(formState.message,
                        duration = SnackbarDuration.Short)
                    navigateBack() // Dipindah setelah snackbar selesai
                }
                viewModel.resetSnackBarMessage()
            }
            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(formState.message,
                        duration = SnackbarDuration.Long)
                }
                viewModel.resetSnackBarMessage()
            }
            else -> {}
        }
    }
}

@Composable
fun TugasFormBody(
    insertUiState: InsertUiState,
    onTugasValueChange: (InsertUiEvent) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    teamData: Map<String, Int?>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TugasFormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTugasValueChange,
            modifier = Modifier.fillMaxWidth(),
            teamData = teamData
        )
        Button(
            onClick = onSaveClick,
            colors = ButtonDefaults.buttonColors(colorResource(R.color.primary)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Simpan", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TugasFormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    teamData: Map<String, Int?> // Ditambahkan parameter untuk data tim
) {
    val initialTeam =
        teamData.entries.firstOrNull { it.value == insertUiEvent.idTim }?.key.orEmpty()
    var selectedTeam by remember { mutableStateOf(initialTeam) }
    LaunchedEffect (teamData) {
        if (teamData.isNotEmpty()) {
            selectedTeam = teamData.entries.firstOrNull { it.value == insertUiEvent.idTim }?.key.orEmpty()
            println("Updated selectedTeam: $selectedTeam")
        }
    }
    val optionsStatus = listOf("Belum Mulai", "Sedang Berlangsung", "Selesai")
    val optionsPrio = listOf("Tinggi", "Sedang", "Rendah")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("Pilih Tim")
        TeamSelector(
            teamData = teamData,
            selectedTeam = selectedTeam, // Nilai awal
            onTeamSelected = { idTim ->
                onValueChange(insertUiEvent.copy(idTim = idTim))
                selectedTeam = teamData.entries.firstOrNull { it.value == idTim }?.key ?: ""
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Nama Tugas")
        CustomOutlinedTextField(
            value = insertUiEvent.namaTugas,
            onValueChange = { onValueChange(insertUiEvent.copy(namaTugas = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Deskripsi Tugas")
        CustomOutlinedTextField(
            value = insertUiEvent.deskripsiTugas,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiTugas = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Prioritas")
        optionsPrio.forEach { prio ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(R.color.primary)
                ),
                    selected = insertUiEvent.prioritas == prio,
                    onClick = { onValueChange(insertUiEvent.copy(prioritas = prio)) }
                )
                Text(text = prio)
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Status Tugas")
        optionsStatus.forEach { status ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(colors = RadioButtonDefaults.colors(
                    selectedColor = colorResource(R.color.primary)
                ),
                    selected = insertUiEvent.statusTugas == status,
                    onClick = { onValueChange(insertUiEvent.copy(statusTugas = status)) }
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

@Composable
fun TeamSelector(
    teamData: Map<String, Int?>,
    selectedTeam: String, //parameter untuk nilai awal
    onTeamSelected: (Int) -> Unit
) {
    val teamNames = teamData.keys.toList()

    DropDownWidget(
        selectedValue = selectedTeam, // Tampilkan nilai awal
        options = teamNames,
        onValueChangeEvent = { selectedName ->
            println("Selected Team: $selectedName") // Debug log
            onTeamSelected(teamData[selectedName] ?: 0)
        },
        modifier = Modifier.fillMaxWidth()
    )
}



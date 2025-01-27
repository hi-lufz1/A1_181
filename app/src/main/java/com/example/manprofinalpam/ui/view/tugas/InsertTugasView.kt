package com.example.manprofinalpam.ui.view.tugas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.ui.customwidget.DropDownWidget
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
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
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val teamData by viewModel.timList.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        }
    ) { innerPadding ->
        TugasFormBody(
            insertUiState = viewModel.uiEvent,
            onTugasValueChange = viewModel::updateInsertTugasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTugas()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            teamData = teamData
        )
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
    ) {
        TugasFormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTugasValueChange,
            modifier = Modifier.fillMaxWidth(),
            teamData = teamData
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
fun TugasFormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    teamData: Map<String, Int?> // Ditambahkan parameter untuk data tim
) {
    val initialTeam = teamData.entries.firstOrNull { it.value == insertUiEvent.idTim }?.key.orEmpty()
    var selectedTeam by remember { mutableStateOf(initialTeam) } //tim yang dipilih

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TeamSelector(
            teamData = teamData,
            selectedTeam = selectedTeam, //Kirim nilai awal
            onTeamSelected = { idTim ->
                onValueChange(insertUiEvent.copy(idTim = idTim))
                selectedTeam = teamData.entries.firstOrNull { it.value == idTim }?.key ?: ""
            }
        )


        OutlinedTextField(
            value = insertUiEvent.namaTugas,
            onValueChange = { onValueChange(insertUiEvent.copy(namaTugas = it)) },
            label = { Text("Nama Tugas") },
            placeholder = { Text("Masukkan Nama Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiTugas,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiTugas = it)) },
            label = { Text("Deskripsi Tugas") },
            placeholder = { Text("Masukkan Deskripsi Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        OutlinedTextField(
            value = insertUiEvent.prioritas,
            onValueChange = { onValueChange(insertUiEvent.copy(prioritas = it)) },
            label = { Text("Prioritas") },
            placeholder = { Text("Masukkan Prioritas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.statusTugas,
            onValueChange = { onValueChange(insertUiEvent.copy(statusTugas = it)) },
            label = { Text("Status Tugas") },
            placeholder = { Text("Masukkan Status Tugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
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
        label = "Pilih Tim",
        onValueChangeEvent = { selectedName ->
            onTeamSelected(teamData[selectedName] ?: 0)
        },
        modifier = Modifier.fillMaxWidth()
    )
}



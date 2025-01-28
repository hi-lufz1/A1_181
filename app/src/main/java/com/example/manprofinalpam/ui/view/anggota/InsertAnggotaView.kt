package com.example.manprofinalpam.ui.view.anggota
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.manprofinalpam.ui.viewmodel.anggota.InsertAnggotaUiEvent
import com.example.manprofinalpam.ui.viewmodel.anggota.InsertAnggotaUiState
import com.example.manprofinalpam.ui.viewmodel.anggota.InsertAnggotaVM
import kotlinx.coroutines.launch

@Preview
@Composable
fun InsertAnggotaScreen(
    navigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InsertAnggotaVM = viewModel(factory = PenyediaVM.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val teamData by viewModel.timList.collectAsState()

    Scaffold (
        modifier = modifier,
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
            CustomTopBar(
                title = "Tambah Anggota",
                onBackClick = navigateBack,
                isEditEnabled = false
            )
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
            ) { HorizontalDivider(
                thickness = 5.dp,
                modifier = Modifier.padding(horizontal = 128.dp)
            )
                Spacer(Modifier.padding(8.dp))
                AnggotaFormBody(
                    insertAnggotaUiState = viewModel.uiEvent,
                    onAgtValueChange = viewModel::updateInsertAnggotaState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertAnggota()
                            navigateBack()
                        }
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(),
                    teamData = teamData
                )
            }
        }
    }
}

@Composable
fun AnggotaFormBody(
    insertAnggotaUiState: InsertAnggotaUiState,
    onAgtValueChange: (InsertAnggotaUiEvent) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    teamData: Map<String, Int?>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AnggotaFormInput(
            insertAgtUiEvent = insertAnggotaUiState.insertAnggotaUiEvent,
            onValueChange = onAgtValueChange,
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
fun AnggotaFormInput(
    insertAgtUiEvent: InsertAnggotaUiEvent,
    onValueChange: (InsertAnggotaUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    teamData: Map<String, Int?>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {  val initialTeam =
        teamData.entries.firstOrNull { it.value == insertAgtUiEvent.idTim }?.key.orEmpty()
        var selectedTeam by remember { mutableStateOf(initialTeam) }
        LaunchedEffect (teamData) {
            if (teamData.isNotEmpty()) {
                selectedTeam = teamData.entries.firstOrNull { it.value == insertAgtUiEvent.idTim }?.key.orEmpty()
                println("Updated selectedTeam: $selectedTeam")
            }
        }
        Text("Nama Anggota")
        CustomOutlinedTextField(
            value = insertAgtUiEvent.namaAnggota,
            onValueChange = { onValueChange(insertAgtUiEvent.copy(namaAnggota = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Pilih Tim")
        com.example.manprofinalpam.ui.view.tugas.TeamSelector(
            teamData = teamData,
            selectedTeam = selectedTeam, // Nilai awal
            onTeamSelected = { idTim ->
                onValueChange(insertAgtUiEvent.copy(idTim = idTim))
                selectedTeam = teamData.entries.firstOrNull { it.value == idTim }?.key ?: ""
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Peran")
        CustomOutlinedTextField(
            value = insertAgtUiEvent.peran,
            onValueChange = { onValueChange(insertAgtUiEvent.copy(peran = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        Spacer(modifier = Modifier.padding(4.dp))
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

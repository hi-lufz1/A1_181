package com.example.manprofinalpam.ui.view.tugas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.ui.navigasi.DesUpdateTgs
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tugas.UpdateTugasVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun UpdateTugasScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateTugasVM = viewModel(factory = PenyediaVM.Factory),
    navigateBack: () -> Unit = {},
){
    val coroutineScope = rememberCoroutineScope()
    val teamData by viewModel.timList.collectAsState()

    Scaffold (
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { DesUpdateTgs.titleRes},
            )
        }
    ){
            padding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(15.dp)
        ){
            TugasFormInput(
                insertUiEvent =  viewModel.uiState.insertUiEvent,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                teamData = teamData
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateTugas()
                        navigateBack()
                    }
                } ,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}
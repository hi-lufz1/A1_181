package com.example.manprofinalpam.ui.view.tugas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tugas.DetailTugasVM
import com.example.manprofinalpam.ui.viewmodel.tugas.DetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DetailTugasScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailTugasVM = viewModel(factory = PenyediaVM.Factory),
    navigateBack: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(17.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = viewModel.detailUiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is DetailUiState.Error -> {
                    Text("Error saat memuat data", modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is DetailUiState.Success -> {
                    ItemDetailTugas(tugas = state.tugas)
                }
            }
        }
    }
}

@Composable
fun ItemDetailTugas(
    modifier: Modifier = Modifier,
    tugas: dataTugas
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            ComponentDetailTugas(judul = "ID Tugas", isinya = tugas.idTugas.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "ID Proyek", isinya = tugas.idProyek.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "Nama Proyek", isinya = tugas.namaProyek)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "ID Tim", isinya = tugas.idTim.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "Nama Tim", isinya = tugas.namaTim)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "Nama Tugas", isinya = tugas.namaTugas)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "Deskripsi Tugas", isinya = tugas.deskripsiTugas)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "Prioritas", isinya = tugas.prioritas)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailTugas(judul = "Status", isinya = tugas.statusTugas)
        }
    }
}

@Composable
fun ComponentDetailTugas(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul:",
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

package com.example.manprofinalpam.ui.view.proyek

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.ui.navigasi.DesDetailPry
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.proyek.DetailProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.DetailUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProyekScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailProyekVM = viewModel(factory = PenyediaVM.Factory),
    navigateBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onReadTugas: (String) -> Unit,
    onAddTugas: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { DesDetailPry.titleRes }
            )
        },
        floatingActionButton = {
            if (viewModel.detailUiState is DetailUiState.Success) {
                FloatingActionButton(
                    onClick = {
                        val idProyek =
                            (viewModel.detailUiState as DetailUiState.Success).proyek.idProyek.toString()
                        onEditClick(idProyek)
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(17.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
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
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }

                is DetailUiState.Error -> {
                    Text("Error saat memuat data", modifier = Modifier.align(Alignment.Start))
                }

                is DetailUiState.Success -> {
                    val proyek = state.proyek
                    ItemDetailProyek(proyek = proyek)
                    Button(
                        onClick = { onReadTugas(proyek.idProyek.toString()) },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Lihat Tugas")
                    }
                    Button(
                        onClick = { onAddTugas(proyek.idProyek.toString()) },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Tambah Tugas")
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetailProyek(
    modifier: Modifier = Modifier,
    proyek: dataProyek
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            ComponentDetailProyek(judul = "ID Proyek", isinya = proyek.idProyek.toString())
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailProyek(judul = "Nama Proyek", isinya = proyek.namaProyek)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailProyek(judul = "Deskripsi", isinya = proyek.deskripsiProyek)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailProyek(judul = "Tanggal Mulai", isinya = proyek.tanggalMulai)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailProyek(judul = "Tanggal Berakhir", isinya = proyek.tanggalBerakhir)
            Spacer(modifier = Modifier.padding(5.dp))
            ComponentDetailProyek(judul = "Status", isinya = proyek.statusProyek)
        }
    }
}

@Composable
fun ComponentDetailProyek(
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
            modifier = modifier
        )
        Text(
            text = isinya,
        )
    }
}

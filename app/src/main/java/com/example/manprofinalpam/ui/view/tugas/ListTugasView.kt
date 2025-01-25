package com.example.manprofinalpam.ui.view.tugas

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.ui.view.proyek.OnError
import com.example.manprofinalpam.ui.view.proyek.OnLoading
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tugas.ListTugasUIState
import com.example.manprofinalpam.ui.viewmodel.tugas.ListTugasVM

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TugasScreen(
    navigateToItemEntry: () -> Unit = {},
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    viewModel: ListTugasVM = viewModel(factory = PenyediaVM.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Daftar Tugas") },
                actions = {
                    IconButton(onClick = { viewModel.getTgs() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Tugas")
            }
        }
    ) { innerPadding ->
        TugasStatus(
            uiState = viewModel.pryUIState,
            retryAction = { viewModel.getTgs() },
            modifier = Modifier.padding(innerPadding),
            onEditClick = onEditClick,
            onDeleteClick = {
                viewModel.deleteTgs(it.idTugas.toString())
                viewModel.getTgs()
            },
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun TugasStatus(
    uiState: ListTugasUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataTugas) -> Unit,
    onDetailClick: (String) -> Unit,
    onEditClick: (String) -> Unit
) {
    var tugasToDelete by remember { mutableStateOf<dataTugas?>(null) }

    when (uiState) {
        is ListTugasUIState.Loading -> OnLoading(modifier.fillMaxSize())
        is ListTugasUIState.Success -> if (uiState.tugas.isEmpty()) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada data tugas.")
            }
        } else {
            TugasLayout(
                tugas = uiState.tugas,
                modifier = modifier,
                onEditClick = { onEditClick(it.idTugas.toString()) },
                onDeleteClick = { tugasToDelete = it },
                onDetailClick = {onDetailClick(it.idTugas.toString())}
            )
        }

        is ListTugasUIState.Error -> OnError(retryAction, modifier.fillMaxSize())
    }
    tugasToDelete?.let { tugas ->
        DeleteConfirmationDialog(
            tugas = tugas,
            onConfirm = {
                onDeleteClick(tugas)
                tugasToDelete = null
            },
            onDismiss = { tugasToDelete = null }
        )
    }
}

@Composable
fun TugasLayout(
    tugas: List<dataTugas>,
    modifier: Modifier = Modifier,
    onEditClick: (dataTugas) -> Unit,
    onDetailClick: (dataTugas) -> Unit,
    onDeleteClick: (dataTugas) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tugas) { item ->
            TugasCard(
                tugas = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) },
                onEditClick = { onEditClick(item) }
            )
        }
    }
}

@Composable
fun TugasCard(
    tugas: dataTugas,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataTugas) -> Unit = {},
    onEditClick: (dataTugas) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = tugas.namaTugas,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(0.8f)
                )
                IconButton(onClick = { onEditClick(tugas) }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = null,
                        modifier = Modifier.weight(0.2f)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = tugas.deskripsiTugas, style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(0.8f)
                )
                IconButton(onClick = { onDeleteClick(tugas) }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = null,
                        modifier = Modifier.weight(0.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    tugas: dataTugas,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Hapus")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batal")
            }
        },
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus tugas '${tugas.namaTugas}'?") }
    )
}

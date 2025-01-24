package com.example.manprofinalpam.ui.view.proyek

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekUIState
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekVM

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProyekScreen(
    navigateToItemEntry: () -> Unit = {},
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    viewModel: ListProyekVM = viewModel(factory = PenyediaVM.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Daftar Proyek") },
                actions = {
                    IconButton(onClick = { viewModel.getPry() }) {
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Proyek")
            }
        }
    ) { innerPadding ->
        ProyekStatus(
            uiState = viewModel.pryUIState,
            retryAction = { viewModel.getPry() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onEditlClick = onEditClick,
            onDeleteClick = {
                viewModel.deletePry(it.idProyek.toString())
                viewModel.getPry()
            }
        )
    }
}

@Composable
fun ProyekStatus(
    uiState: ListProyekUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataProyek) -> Unit = {},
    onDetailClick: (String) -> Unit,
    onEditlClick: (String) -> Unit
) {
    var proyekToDelete by remember { mutableStateOf<dataProyek?>(null) }

    when (uiState) {
        is ListProyekUIState.Loading -> OnLoading(modifier.fillMaxSize())
        is ListProyekUIState.Success -> if (uiState.proyek.isEmpty()) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada data proyek.")
            }
        } else {
            ProyekLayout(
                proyek = uiState.proyek,
                modifier = modifier,
                onDetailClick = { onDetailClick(it.idProyek.toString()) },
                onEditClick = { onEditlClick(it.idProyek.toString()) },
                onDeleteClick = { proyekToDelete = it }
            )
        }

        is ListProyekUIState.Error -> OnError(retryAction, modifier.fillMaxSize())
    }
    proyekToDelete?.let { proyek ->
        DeleteConfirmationDialog(
            proyek = proyek,
            onConfirm = {
                onDeleteClick(proyek)
                proyekToDelete = null
            },
            onDismiss = { proyekToDelete = null }
        )
    }

}

@Composable
fun ProyekLayout(
    proyek: List<dataProyek>,
    modifier: Modifier = Modifier,
    onDetailClick: (dataProyek) -> Unit,
    onEditClick: (dataProyek) -> Unit,
    onDeleteClick: (dataProyek) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(proyek) { item ->
            ProyekCard(
                proyek = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) },
                onEditClick = {onEditClick(item)}
            )
        }
    }
}


@Composable
fun ProyekCard(
    proyek: dataProyek,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataProyek) -> Unit = {},
    onEditClick: (dataProyek) -> Unit = {}
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
                    text = proyek.namaProyek,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = {onEditClick(proyek)}) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = proyek.deskripsiProyek, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = "ID: ${proyek.idProyek}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(onClick = { onDeleteClick(proyek) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
//        Image(
//            painter = painterResource(R.drawable.loading),
//            contentDescription = stringResource(R.string.loading)
//        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.loading), contentDescription = ""
//        )
        Text(text = "Terjadi kesalahan.", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}


@Composable
fun DeleteConfirmationDialog(
    proyek: dataProyek,
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
        text = { Text("Apakah Anda yakin ingin menghapus proyek '${proyek.namaProyek}'?") }
    )
}

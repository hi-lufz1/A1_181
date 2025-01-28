package com.example.manprofinalpam.ui.view.proyek

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.ui.customwidget.BottomBar
import com.example.manprofinalpam.ui.customwidget.HomeTopAppBar
import com.example.manprofinalpam.ui.customwidget.OnError
import com.example.manprofinalpam.ui.customwidget.OnLoading
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekUIState
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekVM

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ProyekScreen(
    navigateToItemEntry: () -> Unit = {},
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onTim: () -> Unit,
    onAnggota: () -> Unit,
    onProyek: () -> Unit = {},
    viewModel: ListProyekVM = viewModel(factory = PenyediaVM.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                HomeTopAppBar(
                    title = "Proyek",
                    onRefreshClick = { viewModel.getPry() },
                )
                if (viewModel.pryUIState is ListProyekUIState.Loading) {
                    LinearProgressIndicator(
                        color = colorResource(id = R.color.primary),
                        progress = viewModel.currentProgress,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LinearProgressIndicator(
                        color = colorResource(id = R.color.primary),
                        progress = 1f, // Indikator tetap penuh setelah loading selesai
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = CircleShape,
                modifier = Modifier
                    .padding(16.dp)
                    .size(64.dp),
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Proyek",
                    modifier = Modifier.size(32.dp, 32.dp)
                )
            }
        },
        bottomBar = {
            BottomBar(
                modifier = modifier,
                onTim = onTim,
                onAnggota = onAnggota,
                onProyek = onProyek
            )
        }
    ) { innerPadding ->
        ProyekStatus(
            uiState = viewModel.pryUIState,
            retryAction = { viewModel.getPry() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
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
) {
    var proyekToDelete by remember { mutableStateOf<dataProyek?>(null) }

    when (uiState) {
        is ListProyekUIState.Loading -> {
            val isLoadingComplete = false
            OnLoading(
                isLoadingComplete = isLoadingComplete,
                modifier = modifier.fillMaxSize()
            )
        }

        is ListProyekUIState.Success -> if (uiState.proyek.isEmpty()) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada data proyek.")
            }
        } else {
            ProyekLayout(
                proyek = uiState.proyek,
                modifier = modifier,
                onDetailClick = { onDetailClick(it.idProyek.toString()) },
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
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}


@Composable
fun ProyekCard(
    proyek: dataProyek,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataProyek) -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(0.8f)) {
                    Text(text = proyek.statusProyek, style = MaterialTheme.typography.bodyLarge)
                    Row(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = R.color.primary),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = "${proyek.tanggalMulai} - ${proyek.tanggalBerakhir}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                IconButton(onClick = { onDeleteClick(proyek) }) {
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
    proyek: dataProyek,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss, containerColor = Color.White,
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary))
            ) {
                Text("Hapus", color = Color.White)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Color.White)) {
                Text("Batal", color = colorResource(id = R.color.primary))

            }
        },
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus proyek '${proyek.namaProyek}'?") }
    )
}

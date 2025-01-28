package com.example.manprofinalpam.ui.view.anggota

import android.os.Build
import androidx.annotation.RequiresExtension
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.ui.customwidget.BottomBar
import com.example.manprofinalpam.ui.customwidget.HomeTopAppBar
import com.example.manprofinalpam.ui.customwidget.OnError
import com.example.manprofinalpam.ui.customwidget.OnLoading
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.anggota.ListAnggotaUIState
import com.example.manprofinalpam.ui.viewmodel.anggota.ListAnggotaVM

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview
@Composable
fun AnggotaScreen(
    navigateToItemEntry: () -> Unit = {},
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onProyek: () -> Unit = {},
    onTim: () -> Unit = {},
    onAgt: () -> Unit = {},
    viewModel: ListAnggotaVM = viewModel(factory = PenyediaVM.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                HomeTopAppBar(
                    title = "Anggota",
                    onRefreshClick = { viewModel.getAgt() },
                )
                if (viewModel.agtUIState is ListAnggotaUIState.Loading) {
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
                    imageVector = Icons.Default.Add, contentDescription = "Tambah Anggota",
                    modifier = Modifier.size(32.dp, 32.dp)
                )
            }
        },
        bottomBar = {
            BottomBar(
                modifier = modifier,
                onProyek = onProyek,
                onTim = onTim,
                onAnggota = onAgt
            )
        }
    ) { innerPadding ->
        AnggotaStatus(
            uiState = viewModel.agtUIState,
            retryAction = { viewModel.getAgt() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAgt(it.idAnggota.toString())
                viewModel.getAgt()
            }
        )
    }
}

@Composable
fun AnggotaStatus(
    uiState: ListAnggotaUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataAnggota) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    var anggotaToDelete by remember { mutableStateOf<dataAnggota?>(null) }

    when (uiState) {
        is ListAnggotaUIState.Loading -> {
            val isLoadingComplete = false
            OnLoading(
                isLoadingComplete = isLoadingComplete,
                modifier = modifier.fillMaxSize()
            )
        }

        is ListAnggotaUIState.Success -> if (uiState.anggota.isEmpty()) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada data anggota.")
            }
        } else {
            AnggotaLayout(
                anggota = uiState.anggota,
                modifier = modifier,
                onDetailClick = { onDetailClick(it.idAnggota.toString()) },
                onDeleteClick = { anggotaToDelete = it }
            )
        }

        is ListAnggotaUIState.Error -> OnError(retryAction, modifier.fillMaxSize())
    }
    anggotaToDelete?.let { anggota ->
        DeleteConfirmationDialog(
            anggota = anggota,
            onConfirm = {
                onDeleteClick(anggota)
                anggotaToDelete = null
            },
            onDismiss = { anggotaToDelete = null }
        )
    }
}

@Composable
fun AnggotaLayout(
    anggota: List<dataAnggota>,
    modifier: Modifier = Modifier,
    onDetailClick: (dataAnggota) -> Unit,
    onDeleteClick: (dataAnggota) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(anggota) { item ->
            AnggotaCard(
                anggota = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun AnggotaCard(
    anggota: dataAnggota,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataAnggota) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = anggota.namaAnggota, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(0.8f)) {
                    Text(
                        text = anggota.namaTim ?: "", // Jika null, tampilkan string kosong
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = R.color.primary),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = "${anggota.peran}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
                IconButton(onClick = { onDeleteClick(anggota) }) {
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
    anggota: dataAnggota,
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
        text = { Text("Apakah Anda yakin ingin menghapus '${anggota.namaAnggota}'?") },
    )
}

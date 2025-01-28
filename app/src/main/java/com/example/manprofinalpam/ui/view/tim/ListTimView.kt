package com.example.manprofinalpam.ui.view.tim

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
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.ui.customwidget.BottomBar
import com.example.manprofinalpam.ui.customwidget.HomeTopAppBar
import com.example.manprofinalpam.ui.view.proyek.OnError
import com.example.manprofinalpam.ui.view.proyek.OnLoading
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tim.ListTimUIState
import com.example.manprofinalpam.ui.viewmodel.tim.ListTimVM

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview
@Composable
fun TimScreen(
    navigateToItemEntry: () -> Unit = {},
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onPry: () -> Unit = {},
    onAnggota: () -> Unit = {},
    viewModel: ListTimVM = viewModel(factory = PenyediaVM.Factory)
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                HomeTopAppBar(
                    title = "Tim",
                    onRefreshClick = { viewModel.getTim() },
                )
                if (viewModel.timUIState is ListTimUIState.Loading) {
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
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Tim")
            }
        },
        bottomBar = {
            BottomBar(
                modifier = modifier,
                onProyek = { onPry },
                onAnggota = { onAnggota },
            )
        }
    ) { innerPadding ->
        TimStatus(
            uiState = viewModel.timUIState,
            retryAction = { viewModel.getTim() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTim(it.idTim.toString())
                viewModel.getTim()
            }
        )
    }
}

@Composable
fun TimStatus(
    uiState: ListTimUIState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataTim) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    var timToDelete by remember { mutableStateOf<dataTim?>(null) }

    when (uiState) {
        is ListTimUIState.Loading -> {
            val isLoadingComplete = false
            OnLoading(
                isLoadingComplete = isLoadingComplete,
                modifier = modifier.fillMaxSize()
            )
        }

        is ListTimUIState.Success -> if (uiState.tim.isEmpty()) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tidak ada data tim.")
            }
        } else {
            TimLayout(
                tim = uiState.tim,
                modifier = modifier,
                onDetailClick = { onDetailClick(it.idTim.toString()) },
                onDeleteClick = { timToDelete = it }
            )
        }

        is ListTimUIState.Error -> OnError(retryAction, modifier.fillMaxSize())
    }

    timToDelete?.let { tim ->
        DeleteConfirmationDialog(
            tim = tim,
            onConfirm = {
                onDeleteClick(tim)
                timToDelete = null
            },
            onDismiss = { timToDelete = null }
        )
    }
}

@Composable
fun TimLayout(
    tim: List<dataTim>,
    modifier: Modifier = Modifier,
    onDetailClick: (dataTim) -> Unit,
    onDeleteClick: (dataTim) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tim) { item ->
            TimCard(
                tim = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun TimCard(
    tim: dataTim,
    modifier: Modifier = Modifier,
    onDeleteClick: (dataTim) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tim.namaTim, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = tim.deskripsiTim, style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { onDeleteClick(tim) }) {
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
    tim: dataTim,
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
        text = { Text("Apakah Anda yakin ingin menghapus tim '${tim.namaTim}'?") },
    )
}

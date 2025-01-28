package com.example.manprofinalpam.ui.view.tugas

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
import com.example.manprofinalpam.ui.customwidget.HomeTopAppBar
import com.example.manprofinalpam.ui.customwidget.OnError
import com.example.manprofinalpam.ui.customwidget.OnLoading
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekUIState
import com.example.manprofinalpam.ui.viewmodel.tugas.ListTugasUIState
import com.example.manprofinalpam.ui.viewmodel.tugas.ListTugasVM

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TugasScreen(
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    viewModel: ListTugasVM = viewModel(factory = PenyediaVM.Factory)
) {
    Scaffold(
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
            CustomTopBar(title = "Daftar Tugas", onEditClick = {
            }, onBackClick = navigateBack, isEditEnabled = false)
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
                    .padding(vertical = 16.dp)
            ) {
                HorizontalDivider(
                    thickness = 5.dp,
                    modifier = Modifier.padding(horizontal = 128.dp)
                )
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
        is ListTugasUIState.Loading -> {
            val isLoadingComplete = false
            OnLoading(
                isLoadingComplete = isLoadingComplete,
                modifier = modifier.fillMaxSize()
            )
        }

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
                onDetailClick = { onDetailClick(it.idTugas.toString()) }
            )
        }

        is ListTugasUIState.Error -> OnError(retryAction, modifier.fillMaxSize())
    }
    tugasToDelete?.let { tugas ->
        DeleteConfirmationDialogTgs(
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
                    text = tugas.namaTugas,
                    style = MaterialTheme.typography.titleLarge,
                )
//                IconButton(onClick = { onEditClick(tugas) }) {
//                    Icon(
//                        imageVector = Icons.Default.Edit, contentDescription = null,
//                        modifier = Modifier.weight(0.2f)
//                    )
//                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column (modifier = Modifier.weight(0.8f)) {
                    Text(
                        text = tugas.deskripsiTugas, style = MaterialTheme.typography.bodyLarge,
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
                            text = "${tugas.namaTim}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
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
fun DeleteConfirmationDialogTgs(
    tugas: dataTugas,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
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
        text = { Text("Apakah Anda yakin ingin menghapus tugas '${tugas.namaTugas}'?") }
    )
}

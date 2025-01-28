package com.example.manprofinalpam.ui.view.tim

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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tim.FormState
import com.example.manprofinalpam.ui.viewmodel.tim.InsertTimUiEvent
import com.example.manprofinalpam.ui.viewmodel.tim.InsertTimUiState
import com.example.manprofinalpam.ui.viewmodel.tim.InsertTimVM
import kotlinx.coroutines.launch

@Preview
@Composable
fun InsertTimScreen(
    navigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InsertTimVM = viewModel(factory = PenyediaVM.Factory)
) {
    val snackbarHostState = remember { SnackbarHostState() } // State untuk Snackbar
    val coroutineScope = rememberCoroutineScope()
    val formState = viewModel.formState // Observe state dari ViewModel

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { snackbarData ->
                Snackbar(
                    shape = RoundedCornerShape(8.dp),
                    snackbarData = snackbarData,
                    containerColor = colorResource(id = R.color.primary), // Warna latar snackbar
                    contentColor = Color.White, // Warna teks
                    actionColor = Color.Yellow // Warna tombol aksi (jika ada)
                )
            }
        },
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
                title = "Tambah Tim",
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
            ) {
                HorizontalDivider(
                    thickness = 5.dp,
                    modifier = Modifier.padding(horizontal = 128.dp)
                )
                Spacer(Modifier.padding(8.dp))
                TimFormBody(
                    insertTimUiState = viewModel.uiEvent,
                    onTimValueChange = viewModel::updateInsertTimState,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertTim()
                        }
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(),
                )
            }
        }
    }
    LaunchedEffect(formState) {
        when (formState) {
            is FormState.Success -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(formState.message,
                        duration = SnackbarDuration.Short)
                    navigateBack() // Dipindah setelah snackbar selesai
                }
                viewModel.resetSnackBarMessage()
            }
            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(formState.message,
                        duration = SnackbarDuration.Long)
                }
                viewModel.resetSnackBarMessage()
            }
            else -> {}
        }
    }
}

@Composable
fun TimFormBody(
    insertTimUiState: InsertTimUiState,
    onTimValueChange: (InsertTimUiEvent) -> Unit = {},
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TimFormInput(
            insertTimUiEvent = insertTimUiState.insertTimUiEvent,
            onValueChange = onTimValueChange,
            modifier = Modifier.fillMaxWidth(),
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
fun TimFormInput(
    insertTimUiEvent: InsertTimUiEvent,
    onValueChange: (InsertTimUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("Nama Tim")
        CustomOutlinedTextField(
            value = insertTimUiEvent.namaTim,
            onValueChange = { onValueChange(insertTimUiEvent.copy(namaTim = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text("Deskrisi Tim")
        CustomOutlinedTextField(
            value = insertTimUiEvent.deskripsiTim,
            onValueChange = { onValueChange(insertTimUiEvent.copy(deskripsiTim = it)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(10.dp),
                color = Color.Red
            )
        }
        HorizontalDivider(
            thickness = 5.dp,
            modifier = Modifier.padding(5.dp)
        )
    }

}

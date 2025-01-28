package com.example.manprofinalpam.ui.view.tim


import com.example.manprofinalpam.ui.viewmodel.tim.DetailTimVM
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.manprofinalpam.R
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.tim.DetailUiState

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DetailTimScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailTimVM = viewModel(factory = PenyediaVM.Factory),
    navigateBack: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

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
            CustomTopBar(title = "Detail Tim", onEditClick = {
                onEditClick()
            }, onBackClick = navigateBack, isEditEnabled = true)
            Spacer(
                Modifier
                    .height(16.dp)
                    .background(color = colorResource(R.color.primary))
            )
            when (val state = viewModel.detailUiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is DetailUiState.Error -> {
                    Text(
                        "Error saat memuat data",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is DetailUiState.Success -> {
                    ItemDetailTim(
                        tim = state.tim,
                        onDeleteClick = { showDeleteDialog = true }
                    )
                }
            }

            if (showDeleteDialog && viewModel.detailUiState is DetailUiState.Success) {
                val tim = (viewModel.detailUiState as DetailUiState.Success).tim
                DeleteConfirmationDialog(
                    tim = tim,
                    onConfirm = {
                        viewModel.deleteTim(tim.idTim.toString())
                        showDeleteDialog = false
                        navigateBack()
                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }
        }
    }
}


@Composable
fun ItemDetailTim(
    modifier: Modifier = Modifier,
    tim: dataTim,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.primary))
    ) {
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

            // Header Section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = tim.namaTim,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.9f)
                )
                Text(
                    text = "ID : ${tim.idTim}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(0.1f)
                )
            }
            Spacer(Modifier.padding(8.dp))

            // Description Section
            Text(
                text = tim.deskripsiTim,
                fontSize = 24.sp,
            )
            Spacer(Modifier.padding(8.dp))
                Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDeleteClick,

                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Hapus Tim", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

    }
}




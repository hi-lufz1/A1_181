package com.example.manprofinalpam.ui.view.anggota

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
import com.example.manprofinalpam.ui.viewmodel.PenyediaVM
import com.example.manprofinalpam.ui.viewmodel.anggota.DetailAnggotaVM
import com.example.manprofinalpam.ui.viewmodel.anggota.DetailUiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailAnggotaScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailAnggotaVM = viewModel(factory = PenyediaVM.Factory),
    navigateBack: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onRefreshDetail:(String)->Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
            CustomTopBar(title = "Detail Anggota", onEditClick = {
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
                    ItemDetailAnggota(
                        anggota = state.anggota,
                        onDeleteClick = { showDeleteDialog = true },
                        onDelFormTim = {
                            coroutineScope.launch {
                                viewModel.delAnggotaFromTim()
                                onRefreshDetail(state.anggota.idAnggota.toString())
                            }
                        }
                    )
                }
            }

            if (showDeleteDialog && viewModel.detailUiState is DetailUiState.Success) {
                val anggota = (viewModel.detailUiState as DetailUiState.Success).anggota
                DeleteConfirmationDialog(
                    anggota = anggota,
                    onConfirm = {
                        viewModel.deleteAgt(anggota.idAnggota.toString())
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
fun ItemDetailAnggota(
    modifier: Modifier = Modifier,
    anggota: dataAnggota,
    onDeleteClick: () -> Unit,
    onDelFormTim:(String)-> Unit
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
                    text = anggota.namaAnggota,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8f)
                )
                Text(
                    text = "ID : ${anggota.idAnggota}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(0.1f)
                )
            }
            Spacer(Modifier.padding(8.dp))

            // Description Section
            Text(
                text = anggota.peran,
                fontSize = 24.sp,
            )
            Spacer(Modifier.padding(8.dp))

            // Details Section
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ComponentDetailAnggota(
                        judul = "Nama Tim",
                        isinya = anggota.namaTim?: "-",
                        modifier = Modifier.weight(0.9f)
                    )

                    Text(
                        text = "ID : ${anggota.idTim ?: "-"}",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(0.1f)
                    )

                }
                Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                    Button(
                        modifier = Modifier.fillMaxWidth().border(
                            width = 1.dp,
                            color = colorResource(id = R.color.primary),
                            shape = RoundedCornerShape(8.dp)
                        ),
                        onClick = { onDelFormTim(anggota.idAnggota.toString()) },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Hapus dari Tim",
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.primary)
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDeleteClick,

                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Hapus Anggota", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ComponentDetailAnggota(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = isinya,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}



package com.example.manprofinalpam.ui.view.proyek

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.ui.customwidget.CustomTopBar
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
            CustomTopBar(title = "Detail Proyek", onEditClick = {
                val idProyek =
                    (viewModel.detailUiState as DetailUiState.Success).proyek.idProyek.toString()
                onEditClick(idProyek)
            }, onBackClick = navigateBack, isEditEnabled = true)
            Spacer(
                Modifier
                    .height(16.dp)
                    .background(color = colorResource(R.color.primary))
            )
            when (val state = viewModel.detailUiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }

                is DetailUiState.Error -> {
                    Text("Error saat memuat data", modifier = Modifier.align(Alignment.Start))
                }

                is DetailUiState.Success -> {
                    val proyek = state.proyek
                    ItemDetailProyek(
                        proyek = proyek,
                        onReadTugas = onReadTugas,
                        onAddTugas = onAddTugas
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailProyek(
    modifier: Modifier = Modifier,
    proyek: dataProyek,
    onReadTugas: (String) -> Unit = {},
    onAddTugas: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = (colorResource(id = R.color.primary)))
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "${proyek.namaProyek}",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ID : ${proyek.idProyek}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Spacer(Modifier.padding(8.dp))
            Text(
                "${proyek.deskripsiProyek}",
                fontSize = 24.sp,
            )
            Column {
                Spacer(Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Tanggal Mulai",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${proyek.tanggalMulai}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }

                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Tanggal Berakhir",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${proyek.tanggalBerakhir}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(Modifier.padding(8.dp))
                Text(
                    text = "Status",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${proyek.statusProyek}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
            )
            Column (modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onReadTugas(proyek.idProyek.toString()) },

                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Lihat Tugas", fontWeight = FontWeight.Bold)
                }
                Spacer(
                    modifier = Modifier
                        .padding(4.dp)
                )

                Button(
                    modifier = Modifier.fillMaxWidth().border(
                        width = 1.dp,
                        color = colorResource(id = R.color.primary),
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = { onAddTugas(proyek.idProyek.toString()) },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Tambah Tugas",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.primary)
                    )

                }
                Spacer(
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }
}

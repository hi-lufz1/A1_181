package com.example.manprofinalpam.ui.view.proyek

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekVM


@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ProyekCardPreview() {
    ProyekCard(
        proyek = dataProyek(
            idProyek = 1,
            namaProyek = "Proyek A",
            deskripsiProyek = "Deskripsi proyek A",
            statusProyek = "",
            tanggalMulai = "",
            tanggalBerakhir = ""
        )
    )
}

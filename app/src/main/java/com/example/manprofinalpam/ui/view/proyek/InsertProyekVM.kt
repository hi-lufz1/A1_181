package com.example.manprofinalpam.ui.view.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.repository.ProyekRepository
import kotlinx.coroutines.launch


class InsertProyekVM(private val pry: ProyekRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPryState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUIEvent = insertUiEvent)
    }

    suspend fun insertPry() {
        viewModelScope.launch {
            try {
                pry.insertProyek(uiState.insertUIEvent.toPry())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiState(
    val insertUIEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val namaProyek: String = "",
    val deskripsiProyek: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val statusProyek: String = ""
)

// simpan input form ke dalam entitiy
fun InsertUiEvent.toPry(): dataProyek = dataProyek(
    idProyek = null,
    namaProyek = namaProyek,
    deskripsiProyek = deskripsiProyek,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    statusProyek = statusProyek
)

fun dataProyek.toUiStatePry(): InsertUiState = InsertUiState(
    insertUIEvent = toInsertUiEvent()
)

fun dataProyek.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    namaProyek = namaProyek,
    deskripsiProyek = deskripsiProyek,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    statusProyek = statusProyek
)
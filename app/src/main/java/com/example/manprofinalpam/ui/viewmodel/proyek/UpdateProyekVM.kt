package com.example.manprofinalpam.ui.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.repository.ProyekRepository
import kotlinx.coroutines.launch
import android.util.Log
import com.example.manprofinalpam.ui.navigasi.DesUpdatePry

class UpdateProyekVM(
    savedStateHandle: SavedStateHandle,
    private val repository: ProyekRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set

   val _idProyek: String = checkNotNull(savedStateHandle[DesUpdatePry.idPry])

    init {
        viewModelScope.launch {
            try {
                val proyek = repository.getProyekByID(_idProyek)
                uiState = proyek.data.toUiStatePryUpdate()
            } catch (e: Exception) {
                Log.e("UpdateProyekVM", "Gagal memuat data proyek: ${e.message}")
                formState = FormState.Error("Gagal memuat data proyek")
            }
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun updateProyek() {
            viewModelScope.launch {
                formState = FormState.Loading
                try {
                    val proyek = uiState.insertUiEvent.toPry()
                    repository.updateProyek(_idProyek, proyek)
                    formState = FormState.Success("Proyek berhasil diperbarui")
                    Log.d("UpdateProyekVM", "Berhasil memperbarui proyek")
                } catch (e: Exception) {
                    Log.e("UpdateProyekVM", "Gagal memperbarui proyek: ${e.message}")
                    formState = FormState.Error("Proyek gagal diperbarui: ${e.message}")
                }
            }
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}


fun dataProyek.toUiStatePryUpdate(): InsertUiState {
    return InsertUiState(
        insertUiEvent = InsertUiEvent(
            namaProyek = namaProyek,
            deskripsiProyek = deskripsiProyek,
            tanggalMulai = tanggalMulai,
            tanggalBerakhir = tanggalBerakhir,
            statusProyek = statusProyek
        )
    )
}

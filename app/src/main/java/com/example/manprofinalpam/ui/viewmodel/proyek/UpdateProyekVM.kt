package com.example.manprofinalpam.ui.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.repository.ProyekRepository
import com.example.manprofinalpam.ui.navigasi.DesUpdatePry
import kotlinx.coroutines.launch


class UpdateProyekVM(
    savedStateHandle: SavedStateHandle,
    private val repository: ProyekRepository,
) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set

    private val _idProyek: String = checkNotNull(savedStateHandle[DesUpdatePry.idPry])

    init {
       loadProyek()
    }

    // Memuat data proyek berdasarkan idProyek
    fun loadProyek() {
        viewModelScope.launch {
            try {
                val proyek = repository.getProyekByID(_idProyek)
                uiState = uiState.copy(
                    idProyek = proyek.data.idProyek,
                    namaProyek = proyek.data.namaProyek,
                    deskripsiProyek = proyek.data.deskripsiProyek,
                    tanggalMulai = proyek.data.tanggalMulai,
                    tanggalBerakhir = proyek.data.tanggalBerakhir,
                    statusProyek = proyek.data.statusProyek
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateField(fieldName: String, value: String) {
        uiState = when (fieldName) {
            "namaProyek" -> uiState.copy(namaProyek = value)
            "deskripsiProyek" -> uiState.copy(deskripsiProyek = value)
            "tanggalMulai" -> uiState.copy(tanggalMulai = value)
            "tanggalBerakhir" -> uiState.copy(tanggalBerakhir = value)
            "statusProyek" -> uiState.copy(statusProyek = value)
            else -> uiState
        }
    }

    fun updateProyek(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val proyek = dataProyek(
                    idProyek = uiState.idProyek,
                    namaProyek = uiState.namaProyek,
                    deskripsiProyek = uiState.deskripsiProyek,
                    tanggalMulai = uiState.tanggalMulai,
                    tanggalBerakhir = uiState.tanggalBerakhir,
                    statusProyek = uiState.statusProyek
                )
                repository.updateProyek(proyek.idProyek.toString(), proyek)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }
}


data class UpdateUiState(
    val idProyek: Int? = null,
    val namaProyek: String = "",
    val deskripsiProyek: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val statusProyek: String = ""
)

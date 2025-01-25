package com.example.manprofinalpam.ui.viewmodel.anggota

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.repository.AnggotaRepository
import com.example.manprofinalpam.repository.TimRepository
import com.example.manprofinalpam.ui.navigasi.DesUpdateAgt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateAnggotaVM(
    savedStateHandle: SavedStateHandle,
    private val repository: AnggotaRepository,
    private val timRepo: TimRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertAnggotaUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set
    private val _timList = MutableStateFlow<Map<String, Int?>>(emptyMap())
    val timList = _timList.asStateFlow()

    private val _idAgt: String = checkNotNull(savedStateHandle[DesUpdateAgt.idAgt])

    init {
        getTimList()
        viewModelScope.launch {
            formState = FormState.Loading
            try {
                val anggota = repository.getAnggotaByID(_idAgt)
                uiState = anggota.data.toUiStateAgtUpdate()
            } catch (e: Exception) {
                Log.e("UpdateAnggotaVM", "Gagal memuat data anggota: ${e.message}")
                formState = FormState.Error("Gagal memuat data anggota")
            }
        }
    }

    private fun getTimList() {
        viewModelScope.launch {
            try {
                val response = timRepo.getTim()
                if (response.status) {
                    // Konversi List<dataTim> ke Map<String, Int>
                    _timList.value = response.data.associate { it.namaTim to it.idTim }
                    Log.d("Data", "list tim  ${timList.value}") //cek data tim
                } else {
                    _timList.value = emptyMap()
                    formState = FormState.Error("Gagal mengambil daftar tim: ${response.message}")
                }
            } catch (e: Exception) {
                _timList.value = emptyMap()
                formState = FormState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun updateState(insertAnggotaUiEvent: InsertAnggotaUiEvent) {
        uiState = InsertAnggotaUiState(
            insertAnggotaUiEvent = insertAnggotaUiEvent
        )
    }

    fun updateAnggota() {
        viewModelScope.launch {
            formState = FormState.Loading
            try {
                val anggota = uiState.insertAnggotaUiEvent.toAnggota()
                repository.updateAnggota(_idAgt, anggota)
                formState = FormState.Success("Anggota berhasil diperbarui")
                Log.d("UpdateAnggotaVM", "Berhasil memperbarui anggota")
            } catch (e: Exception) {
                Log.e("UpdateAnggotaVM", "Gagal memperbarui anggota: ${e.message}")
                formState = FormState.Error("Gagal memperbarui anggota: ${e.message}")
            }
        }
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}


fun dataAnggota.toUiStateAgtUpdate(): InsertAnggotaUiState {
    return InsertAnggotaUiState(
        insertAnggotaUiEvent = InsertAnggotaUiEvent(
            idTim = idTim,
            namaAnggota = namaAnggota,
            peran = peran
        )
    )
}


package com.example.manprofinalpam.ui.viewmodel.tugas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.repository.TimRepository
import com.example.manprofinalpam.repository.TugasRepository
import com.example.manprofinalpam.ui.navigasi.DesUpdateTgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateTugasVM(
    savedStateHandle: SavedStateHandle,
    private val repository: TugasRepository,
    private val timRepo: TimRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set
    private val _timList = MutableStateFlow<Map<String, Int>>(emptyMap())
    val timList = _timList.asStateFlow()

    private val _idTugas: String = checkNotNull(savedStateHandle[DesUpdateTgs.idTgs])
    private var _idProyek: Int = 0

    init {
        getTimList()
        viewModelScope.launch {
            try {
                val tugas = repository.getTugasByID(_idTugas)
                uiState = tugas.data.toUiStateTgsUpdate()
                _idProyek = tugas.data.idProyek
            } catch (e: Exception) {
                Log.e("UpdateTugasVM", "Gagal memuat data tugas: ${e.message}")
                formState = FormState.Error("Gagal memuat data tugas")
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
                    Log.d("Data","list tim  ${timList.value}") //cek data tim
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

    fun updateState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(
            insertUiEvent = insertUiEvent
        )
    }

    fun updateTugas() {
        viewModelScope.launch {
            formState = FormState.Loading
            try {
                val tugas = uiState.insertUiEvent.toTugas(_idProyek)
                repository.updateTugas(_idTugas, tugas)
                formState = FormState.Success("Tugas berhasil diperbarui")
                Log.d("UpdateTugasVM", "Berhasil memperbarui tugas")
            } catch (e: Exception) {
                Log.e("UpdateTugasVM", "Gagal memperbarui tugas: ${e.message}")
                formState = FormState.Error("Tugas gagal diperbarui: ${e.message}")
            }
        }
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}


fun dataTugas.toUiStateTgsUpdate(): InsertUiState {
    return InsertUiState(
        insertUiEvent = InsertUiEvent(
            idTim = idTim,
            namaTugas = namaTugas,
            deskripsiTugas = deskripsiTugas,
            prioritas = prioritas,
            statusTugas = statusTugas
        )
    )
}

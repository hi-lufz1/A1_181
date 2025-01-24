package com.example.manprofinalpam.ui.viewmodel.tugas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.repository.TugasRepository
import com.example.manprofinalpam.ui.navigasi.DesUpdateTgs
import kotlinx.coroutines.launch

class UpdateTugasVM(
    savedStateHandle: SavedStateHandle,
    private val repository: TugasRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set

    private val _idTugas: String = checkNotNull(savedStateHandle[DesUpdateTgs.idTgs])
    private val _idProyek: String = checkNotNull(savedStateHandle[DesUpdateTgs.idPry])

    init {
        viewModelScope.launch {
            try {
                val tugas = repository.getTugasByID(_idTugas)
                uiState = tugas.data.toUiStateTgsUpdate()
            } catch (e: Exception) {
                Log.e("UpdateTugasVM", "Gagal memuat data tugas: ${e.message}")
                formState = FormState.Error("Gagal memuat data tugas")
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
                val tugas = uiState.insertUiEvent.toTugas(_idProyek.toInt())
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

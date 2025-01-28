package com.example.manprofinalpam.ui.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.repository.TimRepository
import kotlinx.coroutines.launch
import android.util.Log
import com.example.manprofinalpam.ui.navigasi.DesUpdateTim

class UpdateTimVM(
    savedStateHandle: SavedStateHandle,
    private val repository: TimRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertTimUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set

   val _idTim: String = checkNotNull(savedStateHandle[DesUpdateTim.idTim])

    init {
        viewModelScope.launch {
            try {
                val tim = repository.getTimByID(_idTim)
                uiState = tim.data.toUiStateTimUpdate()
            } catch (e: Exception) {
                Log.e("UpdateTimVM", "Gagal memuat data tim: ${e.message}")
                formState = FormState.Error("Gagal memuat data tim")
            }
        }
    }

    fun updateState(insertTimUiEvent: InsertTimUiEvent) {
        uiState = InsertTimUiState(
            insertTimUiEvent = insertTimUiEvent
        )
    }

    fun updateTim() {
        viewModelScope.launch {
            formState = FormState.Loading
            try {
                val tim = uiState.insertTimUiEvent.toTim()
                repository.updateTim(_idTim, tim)
                formState = FormState.Success("Tim berhasil diperbarui")
                Log.d("UpdateTimVM", "Berhasil memperbarui tim")
            } catch (e: Exception) {
                Log.e("UpdateTimVM", "Gagal memperbarui tim: ${e.message}")
                formState = FormState.Error("Tim gagal diperbarui: ${e.message}")
            }
        }
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}

fun dataTim.toUiStateTimUpdate(): InsertTimUiState {
    return InsertTimUiState(
        insertTimUiEvent = InsertTimUiEvent(
            namaTim = namaTim,
            deskripsiTim = deskripsiTim
        )
    )
}

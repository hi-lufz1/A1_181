package com.example.manprofinalpam.ui.viewmodel.tim

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.repository.TimRepository
import kotlinx.coroutines.launch

class InsertTimVM(private val timRepo: TimRepository) : ViewModel() {
    var uiEvent: InsertTimUiState by mutableStateOf(InsertTimUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui state berdasarkan input pengguna
    fun updateInsertTimState(insertTimUiEvent: InsertTimUiEvent) {
        uiEvent = uiEvent.copy(insertTimUiEvent = insertTimUiEvent)
    }

    // Validasi input pengguna
    fun validateFields(): Boolean {
        val event = uiEvent.insertTimUiEvent
        val errorState = FormErrorState(
            namaTim = if (event.namaTim.isNotEmpty()) null else "Nama tim tidak boleh kosong",
            deskripsiTim = if (event.deskripsiTim.isNotEmpty()) null else "Deskripsi tim tidak boleh kosong"
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data tim setelah validasi
    fun insertTim() {
        if (validateFields()) {
            viewModelScope.launch {
                formState = FormState.Loading
                try {
                    timRepo.insertTim(uiEvent.insertTimUiEvent.toTim())
                    formState = FormState.Success("Tim berhasil disimpan")
                    Log.d("DEBUG", "Menyimpan tim")
                } catch (e: Exception) {
                    Log.e("DEBUG", "Gagal menyimpan tim: ${e.message}")
                    formState = FormState.Error("Tim gagal disimpan: ${e.message}")
                }
            }
        } else {
            formState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiEvent = InsertTimUiState()
        formState = FormState.Idle
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

// State untuk UI
data class InsertTimUiState(
    val insertTimUiEvent: InsertTimUiEvent = InsertTimUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

// Data dari form input
data class InsertTimUiEvent(
    val namaTim: String = "",
    val deskripsiTim: String = ""
)

// Validasi error untuk form
data class FormErrorState(
    val namaTim: String? = null,
    val deskripsiTim: String? = null
) {
    fun isValid(): Boolean {
        return namaTim == null && deskripsiTim == null
    }
}

// Konversi dari `InsertTimUiEvent` ke `dataTim`
fun InsertTimUiEvent.toTim(): dataTim {
    Log.d("DEBUG", "Data tim yang dikirim: $this")
    return dataTim(
        idTim = null, // Asumsikan ini auto-generated di database
        namaTim = namaTim,
        deskripsiTim = deskripsiTim
    )
}

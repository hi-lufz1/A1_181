package com.example.manprofinalpam.ui.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.repository.TugasRepository
import kotlinx.coroutines.launch

class InsertTugasVM(
    savedStateHandle: SavedStateHandle,
    private val tugasRepo: TugasRepository
) : ViewModel() {
    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set

    private val _idProyek: String = checkNotNull(savedStateHandle[DestinasiInsertTgs.idPry])

    // Memperbarui state berdasarkan input pengguna
    fun updateInsertTugasState(insertUiEvent: InsertUiEvent) {
        uiEvent = uiEvent.copy(insertUiEvent = insertUiEvent)
    }

    // Validasi input pengguna
    fun validateFields(): Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            idTim = if (event.idTim > 0) null else "ID Tim harus diisi",
            namaTugas = if (event.namaTugas.isNotEmpty()) null else "Nama tugas tidak boleh kosong",
            deskripsiTugas = if (event.deskripsiTugas.isNotEmpty()) null else "Deskripsi tugas tidak boleh kosong",
            prioritas = if (event.prioritas.isNotEmpty()) null else "Prioritas tidak boleh kosong",
            statusTugas = if (event.statusTugas.isNotEmpty()) null else "Status tugas tidak boleh kosong"
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data tugas setelah validasi
    fun insertTugas() {
        if (validateFields()) {
            viewModelScope.launch {
                formState = FormState.Loading
                try {
                    tugasRepo.insertTugas(uiEvent.insertUiEvent.toTugas(_idProyek.toInt()))
                    formState = FormState.Success("Tugas berhasil disimpan")
                } catch (e: Exception) {
                    formState = FormState.Error("Tugas gagal disimpan: ${e.message}")
                }
            }
        } else {
            formState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiEvent = InsertUiState()
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

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

// Variabel untuk simpan data dari form input
data class InsertUiEvent(
    val idTim: Int = 0,
    val namaTugas: String = "",
    val deskripsiTugas: String = "",
    val prioritas: String = "",
    val statusTugas: String = ""
)

data class FormErrorState(
    val idTim: String? = null,
    val namaTugas: String? = null,
    val deskripsiTugas: String? = null,
    val prioritas: String? = null,
    val statusTugas: String? = null
) {
    fun isValid(): Boolean {
        return idTim == null &&
                namaTugas == null &&
                deskripsiTugas == null &&
                prioritas == null &&
                statusTugas == null
    }
}

// Simpan input form ke dalam entitiy
fun InsertUiEvent.toTugas(_idproyek: Int): dataTugas = dataTugas(
    idTugas = null,
    idProyek = _idproyek,
    namaProyek = "",
    idTim = idTim,
    namaTim = "",
    namaTugas = namaTugas,
    deskripsiTugas = deskripsiTugas,
    prioritas = prioritas,
    statusTugas = statusTugas
)

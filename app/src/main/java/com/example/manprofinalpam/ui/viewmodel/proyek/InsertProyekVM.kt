package com.example.manprofinalpam.ui.viewmodel.proyek

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.repository.ProyekRepository
import kotlinx.coroutines.launch

class InsertProyekVM(private val pry: ProyekRepository) : ViewModel() {
    var uiEvent :InsertUiState by mutableStateOf(InsertUiState())
        private set
    var formState : FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui state berdasarkan input pengguna
    fun updateInsertPryState(insertUiEvent: InsertUiEvent) {
        uiEvent = uiEvent.copy(insertUiEvent = insertUiEvent)
    }

    // Validasi input pengguna
    fun validateFields(): Boolean {
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            namaProyek = if (event.namaProyek.isNotEmpty()) null else "Nama proyek tidak boleh kosong",
            deskripsiProyek = if (event.deskripsiProyek.isNotEmpty()) null else "Deskripsi proyek tidak boleh kosong",
            tanggalMulai = if (event.tanggalMulai.isNotEmpty()) null else "Tanggal mulai tidak boleh kosong",
            tanggalBerakhir = if (event.tanggalBerakhir.isNotEmpty()) null else "Tanggal berakhir tidak boleh kosong",
            statusProyek = if (event.statusProyek.isNotEmpty()) null else "Status proyek tidak boleh kosong"
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data proyek setelah validasi
    fun insertPry() {
        if (validateFields()) {
            viewModelScope.launch {
                formState = FormState.Loading
                try {
                    pry.insertProyek(uiEvent.insertUiEvent.toPry())
                    formState = FormState.Success("Proyek berhasil disimpan",)
                    Log.d("DEBUG", "Menyimpan proyek")
                } catch (e: Exception) {
                    Log.e("DEBUG", "Gagal menyimpan proyek: ${e.message}")
                    formState = FormState.Error("Proyek gagal disimpan: ${e.message}")
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
    val namaProyek: String = "",
    val deskripsiProyek: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val statusProyek: String = ""
)

data class FormErrorState(
    val namaProyek: String? = null,
    val deskripsiProyek: String? = null,
    val tanggalMulai: String? = null,
    val tanggalBerakhir: String? = null,
    val statusProyek: String? = null
) {
    fun isValid(): Boolean {
        return namaProyek == null &&
                deskripsiProyek == null &&
                tanggalMulai == null &&
                tanggalBerakhir == null &&
                statusProyek == null
    }
}

// Simpan input form ke dalam entitiy
fun InsertUiEvent.toPry(): dataProyek {
    Log.d("DEBUG", "Data proyek yang dikirim: $this")
    return dataProyek(
        idProyek = null,
        namaProyek = namaProyek,
        deskripsiProyek = deskripsiProyek,
        tanggalMulai = tanggalMulai,
        tanggalBerakhir = tanggalBerakhir,
        statusProyek = statusProyek
    )
}


package com.example.manprofinalpam.ui.viewmodel.anggota

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.repository.AnggotaRepository
import com.example.manprofinalpam.repository.TimRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InsertAnggotaVM(
    private val anggotaRepo: AnggotaRepository,
    private val timRepo: TimRepository
) : ViewModel() {
    var uiEvent: InsertAnggotaUiState by mutableStateOf(InsertAnggotaUiState())
        private set
    var formState: FormState by mutableStateOf(FormState.Idle)
        private set
    private val _timList = MutableStateFlow<Map<String, Int?>>(emptyMap())
    val timList = _timList.asStateFlow()

    init {
        getTimList()
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

    // Memperbarui state berdasarkan input pengguna
    fun updateInsertAnggotaState(insertAnggotaUiEvent: InsertAnggotaUiEvent) {
        uiEvent = uiEvent.copy(insertAnggotaUiEvent = insertAnggotaUiEvent)
    }

    // Validasi input pengguna
    fun validateFields(): Boolean {
        val event = uiEvent.insertAnggotaUiEvent
        val errorState = FormErrorState(
            namaAnggota = if (event.namaAnggota.isNotEmpty()) null else "Nama anggota tidak boleh kosong",
            peran = if (event.peran.isNotEmpty()) null else "Peran tidak boleh kosong"
        )
        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data anggota setelah validasi
    fun insertAnggota() {
        if (validateFields()) {
            viewModelScope.launch {
                formState = FormState.Loading
                try {
                    anggotaRepo.insertAnggota(uiEvent.insertAnggotaUiEvent.toAnggota())
                    formState = FormState.Success("Anggota berhasil disimpan")
                    Log.d("DEBUG", "Menyimpan anggota")
                } catch (e: Exception) {
                    Log.e("DEBUG", "Gagal menyimpan anggota: ${e.message}")
                    formState = FormState.Error("Anggota gagal disimpan: ${e.message}")
                }
            }
        } else {
            formState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiEvent = InsertAnggotaUiState()
        formState = FormState.Idle
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}

// State untuk UI
data class InsertAnggotaUiState(
    val insertAnggotaUiEvent: InsertAnggotaUiEvent = InsertAnggotaUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

// Data dari form input
data class InsertAnggotaUiEvent(
    val idTim: Int? = 0,
    val namaAnggota: String = "",
    val peran: String = ""
)

// Validasi error untuk form
data class FormErrorState(
    val namaAnggota: String? = null,
    val peran: String? = null
) {
    fun isValid(): Boolean {
        return namaAnggota == null && peran == null
    }
}

// Konversi dari `InsertAnggotaUiEvent` ke `dataAnggota`
fun InsertAnggotaUiEvent.toAnggota(): dataAnggota {
    Log.d("DEBUG", "Data anggota yang dikirim: $this")
    return dataAnggota(
        idAnggota = null,
        idTim = idTim,
        namaTim = "",
        namaAnggota = namaAnggota,
        peran = peran
    )
}

// Form State
sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

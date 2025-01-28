package com.example.manprofinalpam.ui.viewmodel.anggota

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.repository.AnggotaRepository
import com.example.manprofinalpam.ui.navigasi.DesDetailAgt
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val anggota: dataAnggota) : DetailUiState()
    object Error : DetailUiState()
}

class DetailAnggotaVM(
    savedStateHandle: SavedStateHandle,
    private val repository: AnggotaRepository,
) : ViewModel() {

    var detailUiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set
    private val _idAnggota: String = checkNotNull(savedStateHandle[DesDetailAgt.idAgt])

    init {
        getAnggotaDetail()
    }

    fun getAnggotaDetail() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val anggota = repository.getAnggotaByID(_idAnggota).data
                detailUiState = DetailUiState.Success(anggota)
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }

    fun delAnggotaFromTim() {
        viewModelScope.launch {
            try {
                repository.deleteAnggotaFromTim(_idAnggota)
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteAgt(idAnggota: String) {
        viewModelScope.launch {
            try {
                repository.deleteAnggota(idAnggota)
            } catch (e: IOException) {
                ListAnggotaUIState.Error
            } catch (e: HttpException) {
                ListAnggotaUIState.Error
            }
        }
    }

}

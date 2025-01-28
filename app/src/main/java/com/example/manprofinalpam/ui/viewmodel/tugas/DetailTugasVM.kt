package com.example.manprofinalpam.ui.viewmodel.tugas

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.repository.TugasRepository
import com.example.manprofinalpam.ui.navigasi.DesDetailTgs
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekUIState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val tugas: dataTugas) : DetailUiState()
    object Error : DetailUiState()
}

class DetailTugasVM(
    savedStateHandle: SavedStateHandle,
    private val repository: TugasRepository,
) : ViewModel() {

    var detailUiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set
    private val _idTugas: String = checkNotNull(savedStateHandle[DesDetailTgs.idTgs])

    init {
        getTugasDetail()
    }

    fun getTugasDetail() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val tugas = repository.getTugasByID(_idTugas).data
                detailUiState = DetailUiState.Success(tugas)
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteTgs(idTugas: String) {
        viewModelScope.launch {
            try {
                repository.deleteTugas(idTugas)
            } catch (e: IOException) {
                ListTugasUIState.Error
            } catch (e: HttpException) {
                ListTugasUIState.Error
            }
        }
    }
}
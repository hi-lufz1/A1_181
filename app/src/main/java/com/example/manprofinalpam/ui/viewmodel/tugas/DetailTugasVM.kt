package com.example.manprofinalpam.ui.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.repository.TugasRepository
import com.example.manprofinalpam.ui.navigasi.DesDetailTgs
import kotlinx.coroutines.launch

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
}
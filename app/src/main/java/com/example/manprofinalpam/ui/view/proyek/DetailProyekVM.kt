package com.example.manprofinalpam.ui.view.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.repository.ProyekRepository
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val proyek: dataProyek) : DetailUiState()
    object Error : DetailUiState()
}

class DetailProyekVM(
    savedStateHandle: SavedStateHandle,
    private val repository: ProyekRepository,
) : ViewModel() {

    var detailUiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set

    init {
        val idProyek = savedStateHandle.get<String>("idProyek")
        idProyek?.let {
            getProyekDetail(it)
        }
    }

    fun getProyekDetail(idProyek: String) {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val proyek = repository.getProyekByID(idProyek).data
                detailUiState = DetailUiState.Success(proyek)
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }
}
package com.example.manprofinalpam.ui.viewmodel.tim

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.repository.TimRepository
import com.example.manprofinalpam.ui.navigasi.DesDetailTim
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val tim: dataTim) : DetailUiState()
    object Error : DetailUiState()
}

class DetailTimVM(
    savedStateHandle: SavedStateHandle,
    private val repository: TimRepository,
) : ViewModel() {

    var detailUiState by mutableStateOf<DetailUiState>(DetailUiState.Loading)
        private set
    private val _idTim: String = checkNotNull(savedStateHandle[DesDetailTim.idTim])

    init {
        getTimDetail()
    }

    fun getTimDetail() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            try {
                val tim = repository.getTimByID(_idTim).data
                detailUiState = DetailUiState.Success(tim)
            } catch (e: Exception) {
                detailUiState = DetailUiState.Error
            }
        }
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteTim(idTim: String) {
        viewModelScope.launch {
            try {
                repository.deleteTim(idTim)
            } catch (e: IOException) {
                ListTimUIState.Error
            } catch (e: HttpException) {
                ListTimUIState.Error
            }
        }
    }
}
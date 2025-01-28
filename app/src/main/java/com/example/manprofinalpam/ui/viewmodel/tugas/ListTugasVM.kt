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
import com.example.manprofinalpam.ui.navigasi.DesListTgs
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ListTugasUIState {
    data class Success(val tugas: List<dataTugas>) : ListTugasUIState()
    object Error : ListTugasUIState()
    object Loading : ListTugasUIState()
}

class ListTugasVM(
    savedStateHandle: SavedStateHandle,
    private val tgs: TugasRepository
) : ViewModel() {
    var pryUIState: ListTugasUIState by mutableStateOf(ListTugasUIState.Loading)
        private set
    private val _idProyek: String = checkNotNull(savedStateHandle[DesListTgs.idPry])

    init {
        getTgs()
    }

    fun getTgs() {
        viewModelScope.launch {
            pryUIState = ListTugasUIState.Loading
            try {
                val data = tgs.getTugasByProyek(_idProyek).data
                pryUIState = ListTugasUIState.Success(data)
            } catch (e: Exception) {
                println("Error loading data tugas: ${e.message}")
                pryUIState = ListTugasUIState.Error
            }
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteTgs(idTugas: String) {
        viewModelScope.launch {
            try {
                tgs.deleteTugas(idTugas)
            } catch (e: IOException) {
                ListTugasUIState.Error
            } catch (e: HttpException) {
                ListTugasUIState.Error
            }
        }
    }
}

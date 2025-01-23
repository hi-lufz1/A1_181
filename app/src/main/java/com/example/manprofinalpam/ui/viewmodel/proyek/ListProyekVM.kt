package com.example.manprofinalpam.ui.viewmodel.proyek

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.repository.ProyekRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ListProyekUIState {
    data class Success(val proyek: List<dataProyek>) : ListProyekUIState()
    object Error : ListProyekUIState()
    object Loading : ListProyekUIState()
}

class ListProyekVM(private val pry: ProyekRepository) : ViewModel() {
    var pryUIState: ListProyekUIState by mutableStateOf(ListProyekUIState.Loading)
        private set

    init {
        getPry()
    }

    fun getPry() {
        viewModelScope.launch {
            pryUIState = ListProyekUIState.Loading
            try {
                val data = pry.getProyek().data
                pryUIState = ListProyekUIState.Success(data)
            } catch (e: Exception) {
                println("Error loading data proyek: ${e.message}")
                pryUIState = ListProyekUIState.Error
            }
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deletePry(idProyek: String) {
        viewModelScope.launch {
            try {
                pry.deleteProyek(idProyek)
            } catch (e: IOException) {
                ListProyekUIState.Error
            } catch (e: HttpException) {
                ListProyekUIState.Error
            }
        }
    }
}

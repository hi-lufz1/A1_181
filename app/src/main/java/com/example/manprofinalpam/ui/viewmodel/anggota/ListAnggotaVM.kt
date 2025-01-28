package com.example.manprofinalpam.ui.viewmodel.anggota

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.repository.AnggotaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ListAnggotaUIState {
    data class Success(val anggota: List<dataAnggota>) : ListAnggotaUIState()
    object Error : ListAnggotaUIState()
    object Loading : ListAnggotaUIState()
}

class ListAnggotaVM(private val agt: AnggotaRepository) : ViewModel() {
    var agtUIState: ListAnggotaUIState by mutableStateOf(ListAnggotaUIState.Loading)
        private set
    var currentProgress by mutableStateOf(0f)
    init {
        getAgt()
    }

    fun getAgt() {
        viewModelScope.launch {
            agtUIState = ListAnggotaUIState.Loading
            try {
                // Simulate loading delay
                for (i in 1..50) {
                    currentProgress = i / 50f
                    kotlinx.coroutines.delay(10)
                }
                val data = agt.getAnggota().data
                agtUIState = ListAnggotaUIState.Success(data)
            } catch (e: Exception) {
                println("Error loading data anggota: ${e.message}")
                agtUIState = ListAnggotaUIState.Error
            }
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteAgt(idAnggota: String) {
        viewModelScope.launch {
            try {
                agt.deleteAnggota(idAnggota)
            } catch (e: IOException) {
                ListAnggotaUIState.Error
            } catch (e: HttpException) {
                ListAnggotaUIState.Error
            }
        }
    }
}

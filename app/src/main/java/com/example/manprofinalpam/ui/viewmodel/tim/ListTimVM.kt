package com.example.manprofinalpam.ui.viewmodel.tim

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.repository.TimRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ListTimUIState {
    data class Success(val tim: List<dataTim>) : ListTimUIState()
    object Error : ListTimUIState()
    object Loading : ListTimUIState()
}

class ListTimVM(private val tim: TimRepository) : ViewModel() {
    var timUIState: ListTimUIState by mutableStateOf(ListTimUIState.Loading)
        private set
    var currentProgress by mutableStateOf(0f)
    init {
        getTim()
    }

    fun getTim() {
        viewModelScope.launch {
            timUIState = ListTimUIState.Loading
            try {
                    // Simulate loading delay
                    for (i in 1..50) {
                        currentProgress = i / 50f
                        kotlinx.coroutines.delay(10)
                    }
                val data = tim.getTim().data
                timUIState = ListTimUIState.Success(data)
            } catch (e: Exception) {
                println("Error loading data tim: ${e.message}")
                timUIState = ListTimUIState.Error
            }
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun deleteTim(idTim: String) {
        viewModelScope.launch {
            try {
                tim.deleteTim(idTim)
            } catch (e: IOException) {
                ListTimUIState.Error
            } catch (e: HttpException) {
                ListTimUIState.Error
            }
        }
    }
}

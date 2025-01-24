package com.example.manprofinalpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.manprofinalpam.ManageProjectApp
import com.example.manprofinalpam.ui.viewmodel.proyek.DetailProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.InsertProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.UpdateProyekVM
import com.example.manprofinalpam.ui.viewmodel.tugas.InsertTugasVM
import com.example.manprofinalpam.ui.viewmodel.tugas.ListTugasVM

object PenyediaVM{
    val Factory = viewModelFactory {
        initializer {
            InsertProyekVM(
                ManageProjectApp().container.proyekRepository
            )
        }

        initializer {
            ListProyekVM(
                ManageProjectApp().container.proyekRepository
            )
        }

        initializer {
            UpdateProyekVM(
                createSavedStateHandle(),
                ManageProjectApp().container.proyekRepository
            )
        }

        initializer {
            DetailProyekVM(
                createSavedStateHandle(),
                ManageProjectApp().container.proyekRepository
            )
        }

        initializer {
            InsertTugasVM(
                createSavedStateHandle(),
                ManageProjectApp().container.tugasRepository,
                ManageProjectApp().container.timRepository
            )
        }

        initializer {
            ListTugasVM(
                createSavedStateHandle(),
                ManageProjectApp().container.tugasRepository
            )
        }
    }
}
fun CreationExtras.ManageProjectApp():ManageProjectApp  =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManageProjectApp)
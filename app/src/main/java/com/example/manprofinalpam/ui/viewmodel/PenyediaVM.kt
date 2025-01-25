package com.example.manprofinalpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.manprofinalpam.ManageProjectApp
import com.example.manprofinalpam.ui.viewmodel.anggota.DetailAnggotaVM
import com.example.manprofinalpam.ui.viewmodel.anggota.InsertAnggotaVM
import com.example.manprofinalpam.ui.viewmodel.anggota.ListAnggotaVM
import com.example.manprofinalpam.ui.viewmodel.anggota.UpdateAnggotaVM
import com.example.manprofinalpam.ui.viewmodel.proyek.DetailProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.InsertProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.ListProyekVM
import com.example.manprofinalpam.ui.viewmodel.proyek.UpdateProyekVM
import com.example.manprofinalpam.ui.viewmodel.tim.DetailTimVM
import com.example.manprofinalpam.ui.viewmodel.tim.ListTimVM
import com.example.manprofinalpam.ui.viewmodel.tim.UpdateTimVM
import com.example.manprofinalpam.ui.viewmodel.tugas.DetailTugasVM
import com.example.manprofinalpam.ui.viewmodel.tugas.InsertTugasVM
import com.example.manprofinalpam.ui.viewmodel.tugas.ListTugasVM
import com.example.manprofinalpam.ui.viewmodel.tugas.UpdateTugasVM

object PenyediaVM {
    val Factory = viewModelFactory {

        //Proyek
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

        //Tugas
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

        initializer {
            DetailTugasVM(
                createSavedStateHandle(),
                ManageProjectApp().container.tugasRepository
            )
        }

        initializer {
            UpdateTugasVM(
                createSavedStateHandle(),
                ManageProjectApp().container.tugasRepository,
                ManageProjectApp().container.timRepository
            )
        }


        //Tim
        initializer {
            DetailTimVM(
                createSavedStateHandle(),
                ManageProjectApp().container.timRepository
            )
        }
        initializer {
            ListTimVM(
                ManageProjectApp().container.timRepository
            )
        }

        initializer {
            DetailTimVM(
                createSavedStateHandle(),
                ManageProjectApp().container.timRepository
            )
        }

        initializer {
            UpdateTimVM(
                createSavedStateHandle(),
                ManageProjectApp().container.timRepository
            )
        }

        //Anggota
        initializer {
            InsertAnggotaVM(
                ManageProjectApp().container.anggotaRepository,
                ManageProjectApp().container.timRepository
            )
        }

        initializer {
            ListAnggotaVM(
                ManageProjectApp().container.anggotaRepository
            )
        }

        initializer {
            DetailAnggotaVM(
                createSavedStateHandle(),
                ManageProjectApp().container.anggotaRepository
            )
        }

        initializer {
            UpdateAnggotaVM(
                createSavedStateHandle(),
                ManageProjectApp().container.anggotaRepository,
                ManageProjectApp().container.timRepository
            )
        }

    }
}

fun CreationExtras.ManageProjectApp(): ManageProjectApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManageProjectApp)
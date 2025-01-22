package com.example.manprofinalpam

import android.app.Application
import com.example.manprofinalpam.dependenciesinjection.ContainerApp
import com.example.manprofinalpam.dependenciesinjection.InterfaceContainerApp

class ManageProjectApp: Application(){
    lateinit var container: InterfaceContainerApp
    override fun onCreate() {
        super.onCreate()
        container= ContainerApp()
    }
}
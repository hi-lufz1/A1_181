package com.example.manprofinalpam.dependenciesinjection

import com.example.manprofinalpam.repository.AnggotaRepository
import com.example.manprofinalpam.repository.NetworkAnggotaRepository
import com.example.manprofinalpam.repository.NetworkProyekRepository
import com.example.manprofinalpam.repository.NetworkTimRepository
import com.example.manprofinalpam.repository.NetworkTugasRepository
import com.example.manprofinalpam.repository.ProyekRepository
import com.example.manprofinalpam.repository.TimRepository
import com.example.manprofinalpam.repository.TugasRepository
import com.example.manprofinalpam.service.AnggotaService
import com.example.manprofinalpam.service.ProyekService
import com.example.manprofinalpam.service.TimService
import com.example.manprofinalpam.service.TugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface InterfaceContainerApp{
    val anggotaRepository: AnggotaRepository
    val proyekRepository : ProyekRepository
    val timRepository : TimRepository
    val tugasRepository : TugasRepository
}

class ContainerApp : InterfaceContainerApp{
    private val baseUrl = "http://10.0.2.2:3000/api/" //local host diganti ip klo run dihp
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val anggotaService:AnggotaService by lazy { retrofit.create(AnggotaService::class.java) }
    override val anggotaRepository: AnggotaRepository by lazy { NetworkAnggotaRepository(anggotaService) }

    private val proyekService:ProyekService by lazy { retrofit.create(ProyekService::class.java) }
    override val proyekRepository: ProyekRepository by lazy { NetworkProyekRepository(proyekService) }

    private val timService:TimService by lazy { retrofit.create(TimService::class.java) }
    override val timRepository: TimRepository by lazy { NetworkTimRepository(timService) }

    private val tugasService:TugasService by lazy { retrofit.create(TugasService::class.java) }
    override val tugasRepository: TugasRepository by lazy { NetworkTugasRepository(tugasService) }
}
package com.example.manprofinalpam.repository

import com.example.manprofinalpam.model.ProyekResponse
import com.example.manprofinalpam.model.ProyekResponseDetail
import com.example.manprofinalpam.model.dataProyek
import com.example.manprofinalpam.service.ProyekService
import java.io.IOException

interface ProyekRepository {
    suspend fun getProyek(): ProyekResponse

    suspend fun insertProyek(proyek: dataProyek)

    suspend fun updateProyek(idProyek: String, proyek: dataProyek)

    suspend fun deleteProyek(idProyek: String)

    suspend fun getProyekByID(idProyek: String): ProyekResponseDetail
}

class NetworkProyekRepository(
    private val proyekApiService: ProyekService
) : ProyekRepository {
    override suspend fun getProyek(): ProyekResponse {
        return proyekApiService.getProyek()
    }

    override suspend fun insertProyek(proyek: dataProyek) {
        proyekApiService.insertProyek(proyek)
    }

    override suspend fun updateProyek(idProyek: String, proyek: dataProyek) {
        proyekApiService.updateProyek(idProyek, proyek)
    }

    override suspend fun deleteProyek(idProyek: String) {
        try {
            val response = proyekApiService.deleteProyek(idProyek)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Proyek. HTTP Status Code" +
                            "${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProyekByID(idProyek: String): ProyekResponseDetail {
        return proyekApiService.getProyekById(idProyek)
    }
}
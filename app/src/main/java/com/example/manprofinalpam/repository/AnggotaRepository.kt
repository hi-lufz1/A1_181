package com.example.manprofinalpam.repository

import com.example.manprofinalpam.model.AnggotaResponse
import com.example.manprofinalpam.model.AnggotaResponseDetail
import com.example.manprofinalpam.model.dataAnggota
import com.example.manprofinalpam.service.AnggotaService
import java.io.IOException


interface AnggotaRepository {
    suspend fun getAnggota(): AnggotaResponse

    suspend fun insertAnggota(anggota: dataAnggota)

    suspend fun updateAnggota(idAnggota: String, anggota: dataAnggota)

    suspend fun deleteAnggota(idAnggota: String)

    suspend fun getAnggotaByID(idAnggota: String): AnggotaResponseDetail
}

class NetworkAnggotaRepository(
    private val anggotaApiService: AnggotaService
) : AnggotaRepository {
    override suspend fun getAnggota(): AnggotaResponse {
        return anggotaApiService.getAnggota()
    }

    override suspend fun insertAnggota(anggota: dataAnggota) {
        anggotaApiService.insertAnggota(anggota)
    }

    override suspend fun updateAnggota(idAnggota: String, anggota: dataAnggota) {
        anggotaApiService.updateAnggota(idAnggota, anggota)
    }

    override suspend fun deleteAnggota(idAnggota: String) {
        try {
            val response = anggotaApiService.deleteAnggota(idAnggota)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Anggota Tim. HTTP Status Code" +
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

    override suspend fun getAnggotaByID(idAnggota: String): AnggotaResponseDetail {
        return anggotaApiService.getAnggotaById(idAnggota)
    }
}

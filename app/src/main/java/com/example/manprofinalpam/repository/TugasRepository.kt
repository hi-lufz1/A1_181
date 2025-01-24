package com.example.manprofinalpam.repository

import com.example.manprofinalpam.model.TugasResponse
import com.example.manprofinalpam.model.TugasResponseDetail
import com.example.manprofinalpam.model.dataTugas
import com.example.manprofinalpam.service.TugasService
import java.io.IOException

interface TugasRepository {
    suspend fun getTugas(): TugasResponse

    suspend fun insertTugas(tugas: dataTugas)

    suspend fun updateTugas(idTugas: String, tugas: dataTugas)

    suspend fun deleteTugas(idTugas: String)

    suspend fun getTugasByID(idTugas: String): TugasResponseDetail

    suspend fun getTugasByProyek(idProyek: String): TugasResponse
}

class NetworkTugasRepository(
    private val tugasApiService: TugasService
) : TugasRepository {
    override suspend fun getTugas(): TugasResponse {
        return tugasApiService.getTugas()
    }

    override suspend fun insertTugas(tugas: dataTugas) {
        tugasApiService.insertTugas(tugas)
    }

    override suspend fun updateTugas(idTugas: String, tugas: dataTugas) {
        tugasApiService.updateTugas(idTugas, tugas)
    }

    override suspend fun deleteTugas(idTugas: String) {
        try {
            val response = tugasApiService.deleteTugas(idTugas)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Tugas. HTTP Status Code" +
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

    override suspend fun getTugasByID(idTugas: String): TugasResponseDetail {
        return tugasApiService.getTugasById(idTugas)
    }

    override suspend fun getTugasByProyek(idProyek: String): TugasResponse {
        return tugasApiService.getTugasByProyek(idProyek)
    }
}
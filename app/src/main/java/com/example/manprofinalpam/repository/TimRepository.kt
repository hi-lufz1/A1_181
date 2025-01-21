package com.example.manprofinalpam.repository

import com.example.manprofinalpam.model.TimResponse
import com.example.manprofinalpam.model.TimResponseDetail
import com.example.manprofinalpam.model.dataTim
import com.example.manprofinalpam.service.TimService
import java.io.IOException

interface TimRepository {
    suspend fun getTim(): TimResponse

    suspend fun insertTim(tim: dataTim)

    suspend fun updateTim(idTim: String, tim: dataTim)

    suspend fun deleteTim(idTim: String)

    suspend fun getTimByID(idTim: String): TimResponseDetail
}

class NetworkTimRepository(
    private val timApiService: TimService
) : TimRepository {
    override suspend fun getTim(): TimResponse {
        return timApiService.getTim()
    }

    override suspend fun insertTim(tim: dataTim) {
        timApiService.insertTim(tim)
    }

    override suspend fun updateTim(idTim: String, tim: dataTim) {
        timApiService.updateTim(idTim, tim)
    }

    override suspend fun deleteTim(idTim: String) {
        try {
            val response = timApiService.deleteTim(idTim)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Tim. HTTP Status Code" +
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

    override suspend fun getTimByID(idTim: String): TimResponseDetail {
        return timApiService.getTimById(idTim)
    }
}
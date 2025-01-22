package com.example.manprofinalpam.service

import com.example.manprofinalpam.model.TugasResponse
import com.example.manprofinalpam.model.TugasResponseDetail
import com.example.manprofinalpam.model.dataTugas
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TugasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("tugas")
    suspend fun getTugas(): TugasResponse

    @GET("tugas/{id_tugas}")
    suspend fun getTugasById(@Path("id_tugas") idTugas: String): TugasResponseDetail

    @POST("tugas/store")
    suspend fun insertTugas(@Body tugas: dataTugas)

    @PUT("tugas/{id_tugas}")
    suspend fun updateTugas(@Path("id_tugas") idTugas: String, @Body tugas: dataTugas)

    @DELETE("tugas/{id_tugas}")
    suspend fun deleteTugas(@Path("id_tugas") idTugas: String): Response<Void>
}
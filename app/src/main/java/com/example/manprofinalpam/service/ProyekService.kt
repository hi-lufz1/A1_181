package com.example.manprofinalpam.service

import com.example.manprofinalpam.model.ProyekResponse
import com.example.manprofinalpam.model.ProyekResponseDetail
import com.example.manprofinalpam.model.dataProyek
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProyekService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET(".")
    suspend fun getProyek(): ProyekResponse

    @GET("{id_proyek}")
    suspend fun getProyekById(@Path("id_proyek")idProyek:String): ProyekResponseDetail

    @POST("store")
    suspend fun insertProyek(@Body proyek: dataProyek)

    @PUT("{id_proyek}")
    suspend fun updateProyek(@Path("id_proyek")idProyek:String, @Body proyek: dataProyek)

    @DELETE("{id_proyek}")
    suspend fun deleteProyek(@Path("id_proyek")idProyek:String): Response<Void>
}
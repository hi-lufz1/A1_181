package com.example.manprofinalpam.service

import com.example.manprofinalpam.model.TimResponse
import com.example.manprofinalpam.model.TimResponseDetail
import com.example.manprofinalpam.model.dataTim
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET(".")
    suspend fun getTim(): TimResponse

    @GET("{id_tim}")
    suspend fun getTimById(@Path("id_tim") idTim: String): TimResponseDetail

    @POST("store")
    suspend fun insertTim(@Body tim: dataTim)

    @PUT("{id_tim}")
    suspend fun updateTim(@Path("id_tim") idTim: String, @Body tim: dataTim)

    @DELETE("{id_tim}")
    suspend fun deleteTim(@Path("id_tim") idTim: String): Response<Void>
}
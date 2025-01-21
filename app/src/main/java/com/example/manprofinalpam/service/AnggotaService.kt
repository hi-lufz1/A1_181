package com.example.manprofinalpam.service

import com.example.manprofinalpam.model.AnggotaResponse
import com.example.manprofinalpam.model.AnggotaResponseDetail
import com.example.manprofinalpam.model.dataAnggota
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET(".")
    suspend fun getAnggota(): AnggotaResponse

    @GET("{id_anggota}")
    suspend fun getAnggotaById(@Path("id_anggota")idAnggota:String):AnggotaResponseDetail

    @POST("store")
    suspend fun insertAnggota(@Body anggota: dataAnggota)

    @PUT("{id_anggota}")
    suspend fun updateAnggota(@Path("id_anggota")idAnggota:String, @Body anggota: dataAnggota )

    @DELETE("{id_anggota}")
    suspend fun deleteAnggota(@Path("id_anggota")idAnggota:String): Response<Void>
}
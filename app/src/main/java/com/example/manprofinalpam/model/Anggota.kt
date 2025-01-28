package com.example.manprofinalpam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnggotaResponse (
    val status: Boolean,
    val message: String,
    val data: List<dataAnggota>
)

@Serializable
data class AnggotaResponseDetail (
    val status: Boolean,
    val message: String,
    val data: dataAnggota
)

@Serializable
data class dataAnggota (
    @SerialName("id_anggota")
    val idAnggota: Int?,

    @SerialName("id_tim")
    val idTim: Int?,

    @SerialName("nama_tim")
    val namaTim: String?,

    @SerialName("nama_anggota")
    val namaAnggota: String,

    val peran: String
)
package com.example.manprofinalpam.model

import kotlinx.serialization.*

@Serializable
data class ProyekResponse (
    val status: Boolean,
    val message: String,
    val data: List<dataProyek>
)

@Serializable
data class ProyekResponseDetail (
    val status: Boolean,
    val message: String,
    val data: dataProyek
)

@Serializable
data class dataProyek (
    @SerialName("id_proyek")
    val idProyek: Int?,

    @SerialName("nama_proyek")
    val namaProyek: String,

    @SerialName("deskripsi_proyek")
    val deskripsiProyek: String,

    @SerialName("tanggal_mulai")
    val tanggalMulai: String,

    @SerialName("tanggal_berakhir")
    val tanggalBerakhir: String,

    @SerialName("status_proyek")
    val statusProyek: String
)

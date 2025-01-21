package com.example.manprofinalpam.model

import kotlinx.serialization.*


@Serializable
data class TugasResponse (
    val status: Boolean,
    val message: String,
    val data: List<dataTugas>
)

@Serializable
data class dataTugas (
    @SerialName("id_tugas")
    val idTugas: Int,

    @SerialName("id_proyek")
    val idProyek: Int,

    @SerialName("nama_proyek")
    val namaProyek: String,

    @SerialName("id_tim")
    val idTim: Int,

    @SerialName("nama_tim")
    val namaTim: String,

    @SerialName("nama_tugas")
    val namaTugas: String,

    @SerialName("deskripsi_tugas")
    val deskripsiTugas: String,

    val prioritas: String,

    @SerialName("status_tugas")
    val statusTugas: String
)

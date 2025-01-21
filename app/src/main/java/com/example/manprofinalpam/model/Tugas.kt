package com.example.manprofinalpam.model

import kotlinx.serialization.*


@Serializable
data class TugasResponse (
    val status: Boolean,
    val message: String,
    val data: List<tugas>
)

@Serializable
data class tugas (
    @SerialName("id_tugas")
    val idTugas: Long,

    @SerialName("id_proyek")
    val idProyek: Long,

    @SerialName("nama_proyek")
    val namaProyek: String,

    @SerialName("id_tim")
    val idTim: Long,

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

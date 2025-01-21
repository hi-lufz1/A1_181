package com.example.manprofinalpam.model

import kotlinx.serialization.*
@Serializable
data class TimResponse (
    val status: Boolean,
    val message: String,
    val data: List<dataTim>
)

@Serializable
data class dataTim (
    @SerialName("id_tim")
    val idTim: Int,

    @SerialName("nama_tim")
    val namaTim: String,

    @SerialName("deskripsi_tim")
    val deskripsiTim: String
)
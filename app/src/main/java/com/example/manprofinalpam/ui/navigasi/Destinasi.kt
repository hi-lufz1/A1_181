package com.example.manprofinalpam.ui.navigasi

interface Destinasi{
    val route: String
    val titleRes: String
}

object DesListPry : Destinasi{
    override val route = "listPry"
    override val titleRes = "List Proyek"
}

object DesInsertPry : Destinasi{
    override val route = "insertPry"
    override val titleRes = "Insert Proyek"
}

object DesUpdatePry : Destinasi{
    override val route = "editPry"
    override val titleRes = "Edit Proyek"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}

object DesDetailPry : Destinasi{
    override val route = "detailPry"
    override val titleRes = "Detail Proyek"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}

object DesInsertTgs : Destinasi {
    override val route = "insertTgs"
    override val titleRes = "Insert Tugas"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}

    object DesListTgs : Destinasi{
        override val route = "lisTgs"
        override val titleRes = "List Tugas"
        const val idPry = "idPry"
        val routesWithArg = "$route/{$idPry}"
}


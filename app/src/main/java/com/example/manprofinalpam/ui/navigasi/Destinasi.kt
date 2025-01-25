package com.example.manprofinalpam.ui.navigasi

interface Destinasi {
    val route: String
    val titleRes: String
}

// Proyek
object DesListPry : Destinasi {
    override val route = "listPry"
    override val titleRes = "List Proyek"
}

object DesInsertPry : Destinasi {
    override val route = "insertPry"
    override val titleRes = "Insert Proyek"
}

object DesUpdatePry : Destinasi {
    override val route = "editPry"
    override val titleRes = "Edit Proyek"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}

object DesDetailPry : Destinasi {
    override val route = "detailPry"
    override val titleRes = "Detail Proyek"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}


//Tugas
object DesInsertTgs : Destinasi {
    override val route = "insertTgs"
    override val titleRes = "Insert Tugas"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}

object DesListTgs : Destinasi {
    override val route = "lisTgs"
    override val titleRes = "List Tugas"
    const val idPry = "idPry"
    val routesWithArg = "$route/{$idPry}"
}

object DesDetailTgs : Destinasi {
    override val route = "detailTgs"
    override val titleRes = "Detail Tugas"
    const val idTgs = "idTgs"
    val routesWithArg = "$route/{$idTgs}"
}

object DesUpdateTgs : Destinasi {
    override val route = "editTgs"
    override val titleRes = "Edit Tugas"
    const val idTgs = "idTgs"
    val routesWithArg = "$route/{$idTgs}"
}


//Tim
object DesListTim : Destinasi {
    override val route = "listTim"
    override val titleRes = "List Tim"
}

object DesInsertTim : Destinasi {
    override val route = "insertTim"
    override val titleRes = "Insert Tim"
}

object DesUpdateTim : Destinasi {
    override val route = "editTim"
    override val titleRes = "Edit Tim"
    const val idTim = "idTim"
    val routesWithArg = "$route/{$idTim}"
}

object DesDetailTim : Destinasi {
    override val route = "detailTim"
    override val titleRes = "Detail Tim"
    const val idTim = "idTim"
    val routesWithArg = "$route/{$idTim}"
}
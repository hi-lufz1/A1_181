package com.example.manprofinalpam.ui.navigasi

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manprofinalpam.ui.view.anggota.AnggotaScreen
import com.example.manprofinalpam.ui.view.anggota.DetailAnggotaScreen
import com.example.manprofinalpam.ui.view.anggota.InsertAnggotaScreen
import com.example.manprofinalpam.ui.view.anggota.UpdateAnggotaScreen
import com.example.manprofinalpam.ui.view.proyek.DetailProyekScreen
import com.example.manprofinalpam.ui.view.proyek.InsertProyekScreen
import com.example.manprofinalpam.ui.view.proyek.ProyekScreen
import com.example.manprofinalpam.ui.view.proyek.UpdateProyekScreen
import com.example.manprofinalpam.ui.view.tim.DetailTimScreen
import com.example.manprofinalpam.ui.view.tim.InsertTimScreen
import com.example.manprofinalpam.ui.view.tim.TimScreen
import com.example.manprofinalpam.ui.view.tim.UpdateTimScreen
import com.example.manprofinalpam.ui.view.tugas.DetailTugasScreen
import com.example.manprofinalpam.ui.view.tugas.InsertTugasScreen
import com.example.manprofinalpam.ui.view.tugas.TugasScreen
import com.example.manprofinalpam.ui.view.tugas.UpdateTugasScreen


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
@Preview
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DesListPry.route,
        modifier = modifier
    ) {
        //Proyek
        composable(DesListPry.route) {
            ProyekScreen(
                modifier = modifier,
                onDetailClick = { id ->
                    navController.navigate("${DesDetailPry.route}/$id")
                    println(
                        "PengelolaHalaman: ID =  $id"
                    )
                },
                navigateToItemEntry = { navController.navigate(DesInsertPry.route) },
                onTim = { navController.navigate(DesListTim.route) },
                onAnggota = { navController.navigate(DesListAgt.route) }
            )
        }
        composable(route = DesDetailPry.routesWithArg, arguments = listOf(
            navArgument(DesDetailPry.idPry) {
                type = NavType.StringType
            }
        )) {
            val idPry = it.arguments?.getString(DesDetailPry.idPry)
            idPry?.let { id ->
                DetailProyekScreen(
                    modifier = modifier,
                    onEditClick = {
                        navController.navigate("${DesUpdatePry.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    },
                    onReadTugas = {
                        navController.navigate("${DesListTgs.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    },
                    onAddTugas = {
                        navController.navigate("${DesInsertTgs.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    },
                    navigateBack = { navController.navigate(DesListPry.route) }
                )
            }
        }
        composable(DesInsertPry.route) {
            InsertProyekScreen(
                navigateBack = { navController.navigate(DesListPry.route) },
                modifier = modifier
            )
        }
        composable(
            route = DesUpdatePry.routesWithArg, arguments = listOf(
                navArgument(DesUpdatePry.idPry) {
                    type = NavType.StringType
                })
        ) {
            val idPry = it.arguments?.getString(DesUpdatePry.idPry)
            idPry?.let { id ->
                UpdateProyekScreen(
                    modifier = modifier,
                    navigateBack = { navController.popBackStack() },
                    navigateBackDetail = { id ->
                        navController.navigate("${DesDetailPry.route}/$id")
                        println(
                            "PengelolaHalaman: ID =  $id"
                        )
                    }
                )
            }
        }

        //Tugas
        composable(
            route = DesListTgs.routesWithArg, arguments = listOf(
                navArgument(DesListTgs.idPry) {
                    type = NavType.StringType
                })
        ) {
            val idPry = it.arguments?.getString(DesListTgs.idPry)
            idPry?.let {
                TugasScreen(
                    modifier = modifier,
//                    onEditClick = { idTgs ->
//                        navController.navigate("${DesUpdateTgs.route}/$idTgs")
//                        println("PengelolaHalaman: ID = $idTgs")
//                    },
                    onDetailClick = { idTgs ->
                        navController.navigate("${DesDetailTgs.route}/$idTgs")
                        println(
                            "PengelolaHalaman: ID = $idTgs"
                        )
                    },
                    navigateBack = { navController.popBackStack() },
                )
            }
        }
        composable(
            route = DesInsertTgs.routesWithArg, arguments = listOf(
                navArgument(DesInsertTgs.idPry) {
                    type = NavType.StringType
                })
        ) {
            val idPry = it.arguments?.getString(DesInsertTgs.idPry)
            idPry?.let {
                InsertTugasScreen(
                    modifier = modifier,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = DesDetailTgs.routesWithArg, arguments = listOf(
                navArgument(DesDetailTgs.idTgs) {
                    type = NavType.StringType
                })
        ) {
            val idTgs = it.arguments?.getString(DesDetailTgs.idTgs)
            idTgs?.let { id ->
                DetailTugasScreen(
                    modifier = modifier,
                    onEditClick = {
                        navController.navigate("${DesUpdateTgs.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    }, navigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = DesUpdateTgs.routesWithArg, arguments = listOf(
                navArgument(DesUpdateTgs.idTgs) {
                    type = NavType.StringType
                })
        ) {
            val idTgs = it.arguments?.getString(DesUpdateTgs.idTgs)
            idTgs?.let {
                UpdateTugasScreen(
                    modifier = modifier,
                    navigateBack = { navController.popBackStack() },
                    navigateBackDetail = { navController.navigate("${DesDetailTgs.route}/$idTgs") },
                )
            }
        }

        //Tim
        composable(DesListTim.route) {
            TimScreen(
                modifier = modifier,
                onDetailClick = { id ->
                    navController.navigate("${DesDetailTim.route}/$id")
                    println(
                        "PengelolaHalaman: ID =  $id"
                    )
                },
                onPry = { navController.navigate(DesListPry.route) },
                onAnggota = { navController.navigate(DesListAgt.route) },
                navigateToItemEntry = { navController.navigate(DesListTim.route) }
            )
        }

        composable(DesInsertTim.route) {
            InsertTimScreen(
                modifier = modifier,
                navigateBack = { navController.navigate(DesListTim.route) })
        }
        composable(route = DesDetailTim.routesWithArg, arguments = listOf(
            navArgument(DesDetailTim.idTim) {
                type = NavType.StringType
            }
        )) {
            val idTim = it.arguments?.getString(DesDetailTim.idTim)
            idTim?.let { id ->
                DetailTimScreen(
                    modifier = modifier,
                    navigateBack = { navController.navigate(DesListTim.route) },
                    onEditClick = {
                        navController.navigate("${DesUpdateTim.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    },
                )
            }
        }
        composable(
            route = DesUpdateTim.routesWithArg, arguments = listOf(
                navArgument(DesUpdateTim.idTim) {
                    type = NavType.StringType
                })
        ) {
            val idTim = it.arguments?.getString(DesUpdateTim.idTim)
            idTim?.let { id ->
                UpdateTimScreen(
                    modifier = modifier,
                    navigateBack = { navController.popBackStack() },
                    navigateBackDetail = { id ->
                        navController.navigate("${DesDetailTim.route}/$id")
                        println(
                            "PengelolaHalaman: ID =  $id"
                        )
                    }
                )
            }
        }

        //Anggota
        composable(DesListAgt.route)
        {
            AnggotaScreen(
                modifier = modifier,
                onDetailClick = { id ->
                    navController.navigate("${DesDetailAgt.route}/$id")
                    println(
                        "PengelolaHalaman: ID =  $id"
                    )
                },
                navigateToItemEntry = { navController.navigate(DesInsertAgt.route) },
                onTim = { navController.navigate(DesListTim.route) },
                onProyek = { navController.navigate(DesListPry.route) }
            )
        }
        composable(DesInsertAgt.route) {
            InsertAnggotaScreen(
                modifier = modifier,
                navigateBack = { navController.navigate(DesListAgt.route) })
        }
        composable(route = DesDetailAgt.routesWithArg, arguments = listOf(
            navArgument(DesDetailAgt.idAgt) {
                type = NavType.StringType
            }
        )) {
            val idAgt = it.arguments?.getString(DesDetailAgt.idAgt)
            idAgt?.let { id ->
                DetailAnggotaScreen(
                    modifier = modifier,
                    navigateBack = { navController.navigate(DesListAgt.route) },
                    onEditClick = {
                        navController.navigate("${DesUpdateAgt.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    }, onRefreshDetail = {
                        navController.navigate("${DesDetailAgt.route}/$id")
                        println("PengelolaHalaman: ID = $id")
                    }
                )
            }
        }
        composable(
            route = DesUpdateAgt.routesWithArg, arguments = listOf(
                navArgument(DesUpdateAgt.idAgt) {
                    type = NavType.StringType
                })
        ) {
            val idAgt = it.arguments?.getString(DesUpdateAgt.idAgt)
            idAgt?.let { id ->
                UpdateAnggotaScreen(
                    modifier = modifier,
                    navigateBack = { navController.popBackStack() },
                    navigateBackDetail = { id ->
                        navController.navigate("${DesDetailAgt.route}/$id")
                        println(
                            "PengelolaHalaman: ID =  $id"
                        )
                    }
                )
            }
        }
    }
}
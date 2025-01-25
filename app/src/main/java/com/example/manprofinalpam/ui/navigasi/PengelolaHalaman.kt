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
import com.example.manprofinalpam.ui.view.proyek.DetailProyekScreen
import com.example.manprofinalpam.ui.view.proyek.ProyekScreen
import com.example.manprofinalpam.ui.view.proyek.UpdateProyekScreen
import com.example.manprofinalpam.ui.view.tugas.InsertTugasScreen
import com.example.manprofinalpam.ui.view.tugas.TugasScreen


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
        composable(DesListPry.route) {
            ProyekScreen(
                modifier = modifier,
                onDetailClick = { id ->
                    navController.navigate("${DesDetailPry.route}/$id")
                    println(
                        "PengelolaHalaman: ID =  $id"
                    )
                },

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
                )
            }
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
                    modifier = modifier
                )
            }
        }
        composable(
            route = DesListTgs.routesWithArg, arguments = listOf(
                navArgument(DesListTgs.idPry) {
                    type = NavType.StringType
                })
        ) {
            val idPry = it.arguments?.getString(DesListTgs.idPry)
            idPry?.let { id ->
               TugasScreen(
                    modifier = modifier
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
            idPry?.let { id ->
                InsertTugasScreen(
                    modifier = modifier
                )
            }
        }
    }
}
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
            navArgument(DesDetailPry.idPry){
                type = NavType.StringType
            }
        )){
            val id = it.arguments?.getString(DesDetailPry.idPry)
            id?.let { id ->
                DetailProyekScreen(
                    modifier = modifier,
                )
            }
        }
    }
}
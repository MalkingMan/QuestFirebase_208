package com.example.firebase.view.controllNavigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebase.view.route.DestinasiDetail
import com.example.firebase.view.route.DestinasiEdit
import com.example.firebase.view.route.DestinasiEntry
import com.example.firebase.view.route.DestinasiHome
import com.example.firebase.view.HalamanDetail
import com.example.firebase.view.HalamanEdit
import com.example.firebase.view.HalamanEntry
import com.example.firebase.view.HalamanHome

@Composable
fun PetaNavigasi(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(DestinasiHome.route) {
            HalamanHome(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick = { siswaId ->
                    navController.navigate("${DestinasiDetail.route}/$siswaId")
                }
            )
        }

        composable(DestinasiEntry.route) {
            HalamanEntry(
                navigateBack = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        composable(
            "${DestinasiDetail.route}/{siswaId}",
            arguments = listOf(
                navArgument("siswaId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val siswaId = backStackEntry.arguments?.getString("siswaId")
            siswaId?.let {
                HalamanDetail(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiEdit.route}/$it")
                    }
                )
            }
        }

        composable(
            "${DestinasiEdit.route}/{siswaId}",
            arguments = listOf(
                navArgument("siswaId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val siswaId = backStackEntry.arguments?.getString("siswaId")
            siswaId?.let {
                HalamanEdit(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}


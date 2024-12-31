package com.example.infotainment_vehicle_diagnosis_alerts_new.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.infotainment_vehicle_diagnosis_alerts_new.components.MainViewModel
import com.example.infotainment_vehicle_diagnosis_alerts_new.components.VehicleErrorDetails
import com.example.infotainment_vehicle_diagnosis_alerts_new.components.VehicleNotInScanMode

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.BeforeScan.route,
    viewModel: MainViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.BeforeScan.route) {
            VehicleNotInScanMode(navController,viewModel)
        }
        composable(NavigationItem.ErrorReports.route) {
            VehicleErrorDetails(navController, viewModel)
        }
    }
}
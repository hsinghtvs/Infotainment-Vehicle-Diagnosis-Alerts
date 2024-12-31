package com.example.infotainment_vehicle_diagnosis_alerts_new.navigation

enum class Screen {
    VEHCILEBEFORESCAN,
    VEHICLEERRORREPORTS,
}
sealed class NavigationItem(val route: String) {
    object BeforeScan : NavigationItem(Screen.VEHCILEBEFORESCAN.name)
    object ErrorReports : NavigationItem(Screen.VEHICLEERRORREPORTS.name)
}
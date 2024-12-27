package com.example.infotainment_vehicle_diagnosis_alerts_new.navigation

enum class Screen {
    VEHCILEBEFORESCAN,
    VEHICLEAFTERSCAN,
    VEHICLEERRORREPORTS,
    VEHICLEDURINGSCANNING
}
sealed class NavigationItem(val route: String) {
    object BeforeScan : NavigationItem(Screen.VEHCILEBEFORESCAN.name)
    object AfterScan : NavigationItem(Screen.VEHICLEAFTERSCAN.name)
    object ErrorReports : NavigationItem(Screen.VEHICLEERRORREPORTS.name)
    object DuringScanning : NavigationItem(Screen.VEHICLEDURINGSCANNING.name)
}
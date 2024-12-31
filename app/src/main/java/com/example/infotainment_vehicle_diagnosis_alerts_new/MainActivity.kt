package com.example.infotainment_vehicle_diagnosis_alerts_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.compose.rememberNavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.components.MainViewModel
import com.example.infotainment_vehicle_diagnosis_alerts_new.navigation.AppNavHost
import com.example.infotainment_vehicle_diagnosis_alerts_new.ui.theme.InfotainmentVehicleDiagnosisAlertsNewTheme


var widthOfImage by mutableIntStateOf(0)
var heightOfImage by mutableIntStateOf(0)
var isScanning by mutableStateOf(true)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel :MainViewModel by viewModels<MainViewModel>()
            InfotainmentVehicleDiagnosisAlertsNewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .onGloballyPositioned {
                            widthOfImage = it.size.width
                            heightOfImage = it.size.height
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navController = rememberNavController(), viewModel = viewModel)
                }
            }
        }
    }
}


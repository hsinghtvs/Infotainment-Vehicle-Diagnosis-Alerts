package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R

@Composable
fun VehicleNotInScanMode(navController: NavController, viewModel: MainViewModel) {
    var backgroundGradient = Brush.linearGradient(
        listOf(
            Color(0xFF040F36),
            Color(0xFF030A29)
        )
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient),
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = R.drawable.alerts_logo),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "VEHICLE DIGITAL DIAGNOSIS",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                    )
                )
            }

            Box(modifier = Modifier.fillMaxHeight()) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.car_health),
                    contentDescription = "",
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (viewModel.isScanningDone) {
                VehicleError(
                    modifier = Modifier.weight(2f),
                    viewModel = viewModel,
                    navController = navController
                )
            } else {
                if (viewModel.isScanningProgress) {
                    ScanningStarted(
                        modifier = Modifier
                            .weight(2f),
                        viewModel = viewModel,
                        navController = navController
                    )
                } else {
                    ScanningNotStarted(
                        modifier = Modifier.weight(2f),
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }

    BackHandler {
        if (viewModel.isScanningDone) {
            viewModel.isScanningDone = false
            viewModel.isScanningProgress = false
            viewModel.listOfScanningDone.clear()
        } else if (viewModel.isScanningProgress) {
            viewModel.isScanningProgress = false
        } else {
            viewModel.closeApp = true
        }
    }
}
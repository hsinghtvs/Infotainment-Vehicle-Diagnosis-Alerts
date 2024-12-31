package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage

@Composable
fun ScanningNotStarted(modifier: Modifier,viewModel: MainViewModel, navController: NavController) {
    val firstCircleGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF0D1823),
            Color(0xFF143053)
        )
    )
    val outlineCircleGradient = Brush.verticalGradient(
        listOf(
            Color(red = 0f, green = 0.151f, blue = 0.255f, alpha = 1f),
            Color(red = 0f, green = 0.151f, blue = 0.255f, alpha = 0.7f)
        )
    )

    val glassFrontGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF0D1823),
            Color(0xFF0D1823),
            Color(0xFF143053)
        )
    )
    Box(
        modifier = modifier
            .clickable {
                viewModel.listOfScanningDone.clear()
                viewModel.isScanningProgress = true
            }
            .size((heightOfImage / 2).toInt().dp)
            .clip(CircleShape)
            .background(brush = firstCircleGradient),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size((heightOfImage / 3).toInt().dp)
                .clip(CircleShape)
                .background(brush = firstCircleGradient)
                .border(
                    width = 4.dp,
                    brush = outlineCircleGradient,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size((heightOfImage / 6).toInt().dp)
                    .clip(CircleShape)
                    .background(brush = glassFrontGradient),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size((heightOfImage / 20).dp),
                        painter = painterResource(id = R.drawable.scan),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Start Full \nScan Now",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                        )
                    )
                }
            }
        }
    }
}
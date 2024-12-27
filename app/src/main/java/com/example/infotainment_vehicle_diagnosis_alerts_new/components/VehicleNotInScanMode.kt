package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.navigation.NavigationItem

@Composable
fun VehicleNotInScanMode(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF090F26))
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "VEHICLE DIGITAL DIAGNOSIS",
                style = TextStyle(
                    color = Color.White
                )
            )

            val nonSelectedGradient = Brush.verticalGradient(
                listOf(
                    Color(0xFF090F26),
                    Color(0xFF255AF5)
                )
            )

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        brush =
                        nonSelectedGradient,
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomEnd = 30.dp,
                            bottomStart = 30.dp
                        )

                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF3C4042),
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomEnd = 30.dp,
                            bottomStart = 30.dp
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Paid Services",
                    style = TextStyle(
                        color = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))


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

        Row(
            modifier = Modifier.weight(2f).fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .weight(1.7f)
                    .size(
                        height = (com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage / 4).dp,
                        width = (com.example.infotainment_vehicle_diagnosis_alerts_new.widthOfImage / 30).dp
                    ),
                painter = painterResource(id = R.drawable.car_health),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(NavigationItem.DuringScanning.route)
                    }
                    .padding(10.dp)
                    .size((com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage / 4).toInt().dp)
                    .clip(CircleShape)
                    .background(brush = firstCircleGradient),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size((com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage / 5).toInt().dp)
                        .clip(CircleShape)
                        .background(brush = firstCircleGradient)
                        .border(width = 4.dp, brush = outlineCircleGradient, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size((com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage / 8).toInt().dp)
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
                                modifier = Modifier.size((com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage / 20).dp),
                                painter = painterResource(id = R.drawable.scan),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Start Full \nScan Now",
                                style = TextStyle(
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}
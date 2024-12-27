package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage
import com.example.infotainment_vehicle_diagnosis_alerts_new.navigation.NavigationItem
import com.example.infotainment_vehicle_diagnosis_alerts_new.widthOfImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VehicleDuringScanning(navController: NavController, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFF090F26))

            .fillMaxSize()
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
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .weight(1.7f)
                    .size(
                        height = (heightOfImage / 4).dp,
                        width = (widthOfImage / 30).dp
                    ),
                painter = painterResource(id = R.drawable.car_health),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.weight(1f))

            var currentTime by remember { mutableLongStateOf(0) } // 10 for example
            var progress by remember { mutableFloatStateOf(0f) }
            var scanningInProgress by remember { mutableIntStateOf(0) }
            val progressAnimate by animateFloatAsState(
                targetValue = progress,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )

            val context = LocalContext.current

            var progressCompleted by remember {
                mutableStateOf(false)
            }

            if (progressCompleted == false) {
                LaunchedEffect(true) {
                    while (currentTime < 10) {
                        delay(1000L)
                        currentTime++
                        progress = currentTime.toFloat() / 10f
                        scanningInProgress = (progress * 100).toInt()
                        if (progress == 1f) {
                            progressCompleted = true
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .clickable {
                        if (progressCompleted) {
                            navController.navigate(NavigationItem.AfterScan.route)
                            progressCompleted = false
                        } else {
                            Toast
                                .makeText(context, "Please Complete the scan", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .padding(10.dp)
                    .size((heightOfImage / 4).toInt().dp)
                    .clip(CircleShape)
                    .background(brush = firstCircleGradient),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = progressAnimate,
                    modifier = Modifier
                        .padding(10.dp)
                        .size((heightOfImage / 5).toInt().dp)
                        .clip(CircleShape)
                        .background(brush = firstCircleGradient)
                        .border(width = 4.dp, brush = outlineCircleGradient, shape = CircleShape),
                    strokeWidth = 29.dp,
                    color = Color(0xFF295ACB)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(10.dp)
                        .size((heightOfImage / 8).toInt().dp)
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
                            painter = painterResource(id = R.drawable.scanning_icon),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = if (progressCompleted) {
                                "Click for Reports"
                            } else {
                                "Scan ${scanningInProgress} %"
                            },
                            style = TextStyle(
                                color = Color.White
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}
package com.example.infotainment_vehicle_diagnosis_alerts_new.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage
import kotlinx.coroutines.delay

@Composable
fun ScanningStarted(modifier: Modifier, viewModel: MainViewModel, navController: NavController) {


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
                viewModel.scanningIndex = currentTime.toInt()
                delay(1000L)
                viewModel.listOfScanningDone.add(currentTime.toInt())
                currentTime++
                progress = currentTime.toFloat() / 10f
                scanningInProgress = (progress * 100).toInt()
                if (progress == 1f) {
                    progressCompleted = true
                }
            }
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
        ) {
            LazyColumn() {
                itemsIndexed(viewModel.listOfScanning) { index, item ->
                    ShowingErrorCodes(name = item, viewModel = viewModel, index = index)
                }
            }
        }
        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (progressCompleted) {
                        progressCompleted = false
                        viewModel.isScanningDone = true
                    } else {
                        Toast
                            .makeText(
                                context,
                                "Please Complete the scan",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
                .height((heightOfImage / 3).dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .background(brush = firstCircleGradient),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = progressAnimate,
                modifier = Modifier
                    .padding(10.dp)
                    .size((heightOfImage / 4).toInt().dp)
                    .clip(CircleShape)
                    .background(brush = firstCircleGradient)
                    .border(
                        width = 4.dp,
                        brush = outlineCircleGradient,
                        shape = CircleShape
                    ),
                strokeWidth = 29.dp,
                color = Color(0xFF295ACB)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size((heightOfImage / 8).toInt().dp)
                    .clip(CircleShape)
                    .background(brush = glassFrontGradient)
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = if (progressCompleted) {
                            "Click for Reports"
                        } else {
                            "Scanning is Progress"
                        },
                        style = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 8.sp,
                            fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                        )
                    )
                }
            }
        }

    }
}

@Composable
private fun ShowingErrorCodes(name: String, viewModel: MainViewModel, index: Int) {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        if (viewModel.listOfScanningDone.contains(index)) {
            Image(painter = painterResource(id = R.drawable.circle_tick), contentDescription = "")
        } else if (viewModel.scanningIndex == index) {
            Image(painter = painterResource(id = R.drawable.processing), contentDescription = "")
        } else {
            Spacer(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = name,
            style = TextStyle(color = Color.White),
            fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
        )
    }
}
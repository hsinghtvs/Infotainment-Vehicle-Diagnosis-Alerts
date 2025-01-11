package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage
import kotlinx.coroutines.delay

var rotationY by mutableStateOf(0)

@Composable
fun ScanningStarted(modifier: Modifier, viewModel: MainViewModel, navController: NavController) {


    val firstCircleGradient = Brush.radialGradient(
        listOf(
            Color(0xFF1A3182),
            Color(0xFF041042)
        )
    )
    val progressGradient = Brush.radialGradient(
        listOf(
            Color(0xFF1A3182),
            Color(0xFF041042),
            Color(0xFF34B2CB)
        )
    )

    val outlineCircleGradient = Brush.linearGradient(
        listOf(
            Color(0xFFEFAF24).copy(alpha = 0.2f),
            Color(0xFFEFAF24).copy(alpha = 1f)
        )
    )

    val glassFrontGradient = Brush.linearGradient(
        listOf(
            Color(0xFF1D3A8C),
            Color(0xFF051C5D)
        )
    )

    val finishedScanning = Brush.linearGradient(
        listOf(
            Color(0xFF3DED4F).copy(alpha = 0.7f),
            Color(0xFF3DED4F).copy(alpha = 0.7f)
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

    Column {
        if (!viewModel.isScanningDone) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Scan Components",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                )
            )
        }
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ScanningComponentsStarted(modifier = Modifier.weight(2f), viewModel = viewModel)
            Spacer(modifier = Modifier.weight(0.2f))
            Box(
                modifier = Modifier
                    .clickable {
                        viewModel.severityHashMap.clear()
                        viewModel.criticalSeverity = 0
                        viewModel.highSeverity = 0
                        viewModel.lowSeverity = 0
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
                    .size((heightOfImage / 3).dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(brush = firstCircleGradient),
                contentAlignment = Alignment.Center
            ) {
                GradientProgress(
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
                    colors = listOf(
                        Color(0xFFEFAF24),
                        Color(0xFFEFAF24)
                    )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size((heightOfImage / 6).toInt().dp)
                        .clip(CircleShape)
                        .background(
                            brush = if (progressCompleted) {
                                finishedScanning
                            } else {
                                glassFrontGradient
                            }
                        )
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
                                fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.2f))

        }
    }
}

@Composable
private fun ScanningComponentsStarted(viewModel: MainViewModel, modifier: Modifier) {
    val backGroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF000000).copy(alpha = 0f),
            Color(0xFF76ADFF).copy(alpha = 0.2f)
        )
    )
    LazyVerticalStaggeredGrid(modifier = modifier, columns = StaggeredGridCells.Fixed(2)) {
        itemsIndexed(viewModel.scanningComponents) { index, item ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .clickable {

                    }
                    .background(
                        brush = backGroundGradient,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = item,
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                        )
                    )
                    Spacer(modifier = Modifier.weight(0.2f))
                    ShowingErrorCodes(
                        modifier = Modifier.weight(0.5f),
                        index = index,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@Composable
fun GradientProgress(
    modifier: Modifier = Modifier,
    diameter: Dp = 100.dp,
    width: Float = 100f,
    colors: List<Color> = listOf(Color.Cyan, Color.Blue),
    progress: Float = .75f
) {
    Box(
        content = {
            Text(
                text = "${(progress * 100).toInt()}",
                modifier = Modifier.align(Alignment.Center)
            )
            Canvas(
                modifier = modifier
                    .size(diameter)
                    .rotate(-90f)
                    .graphicsLayer {
                        rotationY = 360f
                    },
                onDraw = {
//                    drawArc(
//                        color = Color.LightGray,
//                        startAngle = 0f,
//                        sweepAngle = 360f,
//                        false,
//                        style = Stroke(width = width)
//                    )
                    drawArc(
                        brush = Brush.sweepGradient(colors = colors),
                        startAngle = 0f,
                        sweepAngle = progress * 360f,
                        false,
                        style = Stroke(width = width)
                    )
                }
            )
        }
    )
}

@Composable
private fun ShowingErrorCodes(modifier: Modifier, viewModel: MainViewModel, index: Int) {
    Row(
        modifier = modifier
    ) {
        if (viewModel.listOfScanningDone.contains(index)) {
            if (index == 0) {
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.critical),
                    contentDescription = ""
                )
            } else if (index == 4) {
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.low),
                    contentDescription = ""
                )
            } else {
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.circle_tick),
                    contentDescription = ""
                )
            }
        }
    }
}
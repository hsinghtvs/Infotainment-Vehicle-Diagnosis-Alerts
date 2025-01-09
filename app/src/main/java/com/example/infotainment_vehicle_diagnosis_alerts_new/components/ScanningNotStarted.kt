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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage

@Composable
fun ScanningNotStarted(modifier: Modifier, viewModel: MainViewModel, navController: NavController) {
    val firstCircleGradient = Brush.radialGradient(
        listOf(
            Color(0xFF1A3182),
            Color(0xFF041042)
        )
    )
    val outlineCircleGradient = Brush.linearGradient(
        listOf(
            Color(0xFFFFFFFF).copy(alpha = 0.2f),
            Color(0xFF1FA4F2).copy(alpha = 1f)
        )
    )

    val glassFrontGradient = Brush.linearGradient(
        listOf(
            Color(0xFF1D3A8C),
            Color(0xFF051C5D)
        )
    )

    Column {
        if(!viewModel.isScanningDone){
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
            ScanningComponents(modifier = Modifier.weight(2f), viewModel = viewModel)
            Spacer(modifier = Modifier.weight(0.2f))
            Box(
                modifier = Modifier
                    .clickable {
                        viewModel.listOfScanningDone.clear()
                        viewModel.isScanningProgress = true
                    }
                    .size((heightOfImage / 3).toInt().dp)
                    .clip(CircleShape)
                    .background(brush = firstCircleGradient),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size((heightOfImage / 4).toInt().dp)
                        .clip(CircleShape)
                        .background(brush = firstCircleGradient)
                        .border(
                            width = 1.dp,
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
                                modifier = Modifier.size((heightOfImage / 40).dp),
                                painter = painterResource(id = R.drawable.scan),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Start Full \nScan Now",
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.manrope_bold))
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}

@Composable
private fun ScanningComponents(viewModel: MainViewModel, modifier: Modifier) {
    val backGroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF000000).copy(alpha = 0f),
            Color(0xFF76ADFF).copy(alpha = 0.2f)
        )
    )
    LazyVerticalStaggeredGrid(modifier = modifier, columns = StaggeredGridCells.Fixed(2)) {
        items(viewModel.scanningComponents) {
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
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = it,
                    style = TextStyle(color = Color.White,fontFamily = FontFamily(Font(R.font.manrope_semibold)))
                )
            }
        }
    }
}
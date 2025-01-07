package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.model.Severity
import com.example.infotainment_vehicle_diagnosis_alerts_new.navigation.NavigationItem
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun VehicleError(modifier: Modifier, viewModel: MainViewModel, navController: NavController) {
    ErrorSection(modifier = modifier, navController = navController, viewModel = viewModel)

}


@Composable
fun ErrorSection(modifier: Modifier, navController: NavController, viewModel: MainViewModel) {

    viewModel.severityHashMap.clear()
    val context = LocalContext.current
    val carpmObject = JSONObject(loadErrors(context))
    val filterErrorCodes = JSONArray()
    val groupErrorCodeArray = HashMap<String, ArrayList<JSONObject>>()
    val modules = ArrayList<String>()
    if (carpmObject.getJSONArray("code_details").length() > 0) {

        // Filtering the list
        filterErrorCodes.put(carpmObject.getJSONArray("code_details").getJSONObject(0))
        for (i in 1 until carpmObject.getJSONArray("code_details").length()) {
            var dtcNeedToAdd = false
            if (carpmObject.getJSONArray("code_details").getJSONObject(i)
                    .getString("module") != "null"
            ) {
                if (!filterErrorCodes.toString().contains(
                        carpmObject.getJSONArray("code_details").getJSONObject(i).getString("dtc")
                    )
                ) {
                    filterErrorCodes.put(carpmObject.getJSONArray("code_details").getJSONObject(i))
                } else {
                    for (j in 0 until filterErrorCodes.length()) {
                        if (filterErrorCodes.getJSONObject(j)
                                .getString("dtc") == carpmObject.getJSONArray("code_details")
                                .getJSONObject(i).getString("dtc")
                        ) {
                            dtcNeedToAdd = if (filterErrorCodes.getJSONObject(j)
                                    .getString("module") != carpmObject.getJSONArray("code_details")
                                    .getJSONObject(i).getString("module")
                            ) {
                                true
                            } else {
                                false
                            }
                        }
                    }
                }
                if (dtcNeedToAdd) {
                    filterErrorCodes.put(carpmObject.getJSONArray("code_details").getJSONObject(i))
                }
            }
        }

        // Making group

        for (i in 0 until filterErrorCodes.length()) {
            if (!modules.toString()
                    .contains(filterErrorCodes.getJSONObject(i).getString("module"))
            ) {
                modules.add(filterErrorCodes.getJSONObject(i).getString("module"))
                val emptyList = ArrayList<JSONObject>()
                groupErrorCodeArray.put(
                    filterErrorCodes.getJSONObject(i).getString("module"),
                    emptyList
                )

            }

            if (!viewModel.severityHashMap.contains(
                    filterErrorCodes.getJSONObject(i).getString("module")
                )
            ) {
                // Critical
                val criticalSeverity = Severity("Critical", 0)
                val hashMapCritical = HashMap<String, Severity>()
                hashMapCritical.put("Critical", criticalSeverity)
                viewModel.severityHashMap.put(
                    filterErrorCodes.getJSONObject(i).getString("module"),
                    hashMapCritical
                )

                // High
                val highSeverity = Severity("High", 0)
                hashMapCritical.put("High", highSeverity)
                viewModel.severityHashMap.put(
                    filterErrorCodes.getJSONObject(i).getString("module"),
                    hashMapCritical
                )

                // Low
                val lowSeverity = Severity("Low", 0)
                hashMapCritical.put("Low", lowSeverity)
                viewModel.severityHashMap.put(
                    filterErrorCodes.getJSONObject(i).getString("module"),
                    hashMapCritical
                )
            }

            if (viewModel.severityHashMap.contains(
                    filterErrorCodes.getJSONObject(i).getString("module")
                )
            ) {
                val hashmapSeverity = viewModel.severityHashMap.getValue(
                    filterErrorCodes.getJSONObject(i).getString("module")
                )

                if (filterErrorCodes.getJSONObject(i).getString("severity") == "Critical") {
                    hashmapSeverity.put(
                        "Critical",
                        Severity("Critical", hashmapSeverity.getValue("Critical").count + 1)
                    )
                } else if (filterErrorCodes.getJSONObject(i).getString("severity") == "High") {
                    hashmapSeverity.put(
                        "High",
                        Severity("High", hashmapSeverity.getValue("High").count + 1)
                    )
                } else {
                    hashmapSeverity.put(
                        "Low",
                        Severity("Low", hashmapSeverity.getValue("Low").count + 1)
                    )
                }

            }
        }

        var groupIndex = 0
        while (groupIndex <= filterErrorCodes.length() - 1) {
            val errorName = filterErrorCodes.getJSONObject(groupIndex).getString("module")
            val jsonObject = groupErrorCodeArray.get(errorName)
            val errorJSONObject = JSONObject()
            errorJSONObject.put(
                filterErrorCodes.getJSONObject(groupIndex).getString("dtc"),
                filterErrorCodes.getJSONObject(groupIndex)
            )
            if (jsonObject != null) {
                jsonObject.add(errorJSONObject)
            }

            if (jsonObject != null) {
                groupErrorCodeArray.put(errorName, jsonObject)
            };
            groupIndex++;
        }
    }

    Column {

        val nonSelectedGradient = Brush.verticalGradient(
            listOf(
                Color(0xFF255AF5),
                Color(0xFF090F26)
            )
        )
        if (viewModel.isScanningDone) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewModel.isScanningDone) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Scan Components",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
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
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                        text = "ERROR SECTION",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                        )
                    )
                }
            }
        }

        Row() {
            ScanningComponentsCompleted(modifier = Modifier.weight(2f), viewModel = viewModel)
            Spacer(modifier = Modifier.weight(0.2f))
            ConstraintLayout(
                modifier = modifier.fillMaxHeight()
            ) {
                val (errorLazyColumn, rsaBox) = createRefs()
                LazyColumn(
                    modifier = Modifier
                        .constrainAs(errorLazyColumn) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    items(modules) { moduleName ->
                        groupErrorCodeArray.get(moduleName)?.let { errorCodesList ->
                            ErrorBox(moduleName, navController, errorCodesList, viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScanningComponentsCompleted(viewModel: MainViewModel, modifier: Modifier) {
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
                        text = item,
                        style = TextStyle(color = Color.White, fontSize = 10.sp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    ShowingErrorCodes(index = index, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun ShowingErrorCodes(viewModel: MainViewModel, index: Int) {
    Row(
    ) {
        if (index == 0) {
            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.critical),
                contentDescription = ""
            )
        } else if (index == 4) {
            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.critical),
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

@Composable
private fun ErrorBox(
    moduleName: String,
    navController: NavController,
    errorCodesList: ArrayList<JSONObject>,
    viewModel: MainViewModel
) {

    val severity = viewModel.severityHashMap.getValue(moduleName)
    val critical = severity.getValue("Critical").count
    val high = severity.getValue("High").count
    val low = severity.getValue("Low").count

    val backGroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF000000).copy(alpha = 0f),
            Color(0xFF76ADFF).copy(alpha = 0.2f)
        )
    )

    val nonSelectedGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5),
            Color(0xFF090F26).copy(alpha = 0f)
        )
    )

    Box(
        modifier = Modifier.background(
            brush = backGroundGradient,
            shape = RoundedCornerShape(size = 8.dp)
        )
    ) {

        Column(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    text = moduleName,
                    style = TextStyle(color = Color.White)
                )
                Spacer(modifier = Modifier.weight(1f))
                ClickableText(
                    onClick = {
                        selectedError = 0
                        viewModel.setName(errorCodesList)
                        viewModel.criticalSeverity = critical
                        viewModel.highSeverity = high
                        viewModel.lowSeverity = low
                        navController.navigate(NavigationItem.ErrorReports.route)
                    },
                    modifier = Modifier
                        .background(
                            brush =
                            nonSelectedGradient,
                            shape = CircleShape
                        )
                        .border(1.dp, color = Color.White.copy(alpha = 0.2f), shape = CircleShape)
                        .padding(horizontal = 15.dp, vertical = 5.dp),
                    text = AnnotatedString("View"),
                    style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ErrorCounts(
                    modifier = Modifier.weight(1f),
                    image = R.drawable.critical,
                    name = "Critical",
                    count = critical
                )
                ErrorCounts(
                    modifier = Modifier.weight(1f),
                    image = R.drawable.high,
                    name = "High",
                    count = high
                )
                ErrorCounts(
                    modifier = Modifier.weight(1f),
                    image = R.drawable.low,
                    name = "Low",
                    count = low
                )
            }
        }
    }
}

@Composable
private fun ErrorCounts(
    modifier: Modifier,
    image: Int,
    name: String,
    count: Int
) {

    Row(
        modifier = modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(10.dp),
            painter = painterResource(id = image),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = name, style = TextStyle(color = Color.White, fontSize = 10.sp))
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = count.toString(),
            style = TextStyle(color = Color(0xFF3DED4F), fontSize = 10.sp)
        )
    }

}


fun loadErrors(context: Context): String {
    var json: String? = null
    val inputStream = context.assets.open("faults.json")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    json = String(buffer, Charsets.UTF_8)
    return json.toString()
}
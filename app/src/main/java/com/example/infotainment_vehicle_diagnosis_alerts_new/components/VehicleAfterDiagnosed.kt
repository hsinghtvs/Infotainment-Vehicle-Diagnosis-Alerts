package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import com.example.infotainment_vehicle_diagnosis_alerts_new.heightOfImage
import com.example.infotainment_vehicle_diagnosis_alerts_new.navigation.NavigationItem
import com.example.infotainment_vehicle_diagnosis_alerts_new.navigation.Screen
import com.example.infotainment_vehicle_diagnosis_alerts_new.widthOfImage
import org.json.JSONArray
import org.json.JSONObject


@Composable
fun VehicleAfterDiagnosed(navController: NavController,viewModel: MainViewModel) {
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

            Spacer(modifier = Modifier.size(20.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        text = "ERROR SECTION",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "NON ERROR SECTION",
                    style = TextStyle(
                        color = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.2f))

        Row(
            modifier = Modifier
                .weight(2f)
                .padding(end = 30.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1.5f)
                    .size(
                        height = (heightOfImage / 4).dp,
                        width = (widthOfImage / 30).dp
                    ),
                painter = painterResource(id = R.drawable.car_health),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.weight(0.5f))
            ErrorSection(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                navController,
                viewModel
            )
        }
    }
}


@Composable
fun ErrorSection(modifier: Modifier, navController: NavController,viewModel: MainViewModel) {

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

        for (i in 0 until filterErrorCodes.length() - 1) {
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

    val rsaBoxGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5),
            Color(0xFF090F26),
        )
    )

    var columnWidth by remember { mutableIntStateOf(0) };

    ConstraintLayout(
        modifier = modifier
    ) {
        val (errorLazyColumn, rsaBox) = createRefs()
        LazyColumn(
            modifier = Modifier
                .constrainAs(errorLazyColumn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 77.dp)
        ) {
            items(modules) {
                groupErrorCodeArray.get(it)?.let { errorCodesList ->
                    ErrorBox(it, errorCodesList, navController,viewModel)
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .constrainAs(rsaBox) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 10.dp)
                .background(
                    color = Color(0xFF1D3354),
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF3C4042),
                    shape = RoundedCornerShape(
                        10.dp
                    )
                ),
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.weight(0.2f),
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .onGloballyPositioned {
                            columnWidth = it.size.width
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Critical Errors Found",
                        style = TextStyle(color = Color.White)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = "Missfire in engine cylinder error 204 ",
                        maxLines = 1,
                        style = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Justify
                        )
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                ClickableText(
                    onClick = {
                        // Navigate to RSA App
                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            brush =
                            rsaBoxGradient,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(5.dp),
                    text = AnnotatedString("Book RSA"),
                    style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
                )
            }
        }


    }
}


@Composable
fun ErrorBox(
    nameOfError: String,
    totalErrors: ArrayList<JSONObject>,
    navController: NavController,
    viewModel: MainViewModel
) {
    val nonSelectedGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5),
            Color(0xFF090F26),
        )
    )
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color =
                Color(0xFF1D3354),
                shape = RoundedCornerShape(
                    10.dp
                )
            )
            .border(
                width = 1.dp,
                color = Color(0xFF3C4042),
                shape = RoundedCornerShape(
                    10.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = nameOfError,
                style = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp),
                text = totalErrors.size.toString(),
                style = TextStyle(
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            ClickableText(
                onClick = {
                    viewModel.setName(totalErrors)
                    selectedError = 0
                    navController.navigate(NavigationItem.ErrorReports.route)
                },
                modifier = Modifier
                    .weight(1.3f)
                    .background(
                        brush =
                        nonSelectedGradient,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 15.dp, vertical = 5.dp),
                text = AnnotatedString("View"),
                style = TextStyle(color = Color.White, textAlign = TextAlign.Center)
            )
        }
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
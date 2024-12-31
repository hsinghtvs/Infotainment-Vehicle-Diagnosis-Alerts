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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infotainment_vehicle_diagnosis_alerts_new.R
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


var selectedError by mutableIntStateOf(0)

@Composable
fun VehicleErrorDetails(navController: NavController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val listOfFaults = viewModel.getName()
    var jsonObject by remember {
        mutableStateOf(JSONObject())
    }
        val name = listOfFaults.get(selectedError).keys().next()
        viewModel.setSelectedErrorDetails(
            listOfFaults
                .get(selectedError)
                .getJSONObject(name)
        )
        jsonObject = viewModel.getSelectedErrorDetails();
    val backgroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF0B1112),
            Color(0xFF16345B)
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Engine Errors",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        Row {
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(listOfFaults) { int, faults ->
                    val name = listOfFaults.get(int).keys().next()
                    val meaning =
                        listOfFaults.get(int).getJSONObject(name).get("meaning").toString()
                    val dtc = listOfFaults.get(int).getJSONObject(name).get("dtc").toString()
                    ErrorDescription(
                        Modifier.fillMaxWidth(),
                        meaning,
                        int,
                        name,
                        listOfFaults,
                        viewModel,
                        dtc
                    ) {
                        jsonObject = viewModel.getSelectedErrorDetails();
                    }
                }
            }
            ErrorDetails(modifier = Modifier.weight(1f), jsonObject, viewModel)
            ErrorExplanation(modifier = Modifier.weight(1f), jsonObject, viewModel)

        }
    }
}

@Composable
fun ErrorDescription(
    modifier: Modifier,
    meaning: String,
    errorIndex: Int,
    name: String,
    listOfFaults: ArrayList<JSONObject>,
    viewModel: MainViewModel,
    dtc: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable {
                selectedError = errorIndex
                viewModel.setSelectedErrorDetails(
                    listOfFaults
                        .get(errorIndex)
                        .getJSONObject(name)
                )
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = if (selectedError == errorIndex) {
                    Color(0xFF1F57E7)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = meaning,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                )
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = dtc,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.hankengrotesk_medium))
                )
            )
        }
    }
}

@Composable
fun ErrorDetails(modifier: Modifier, ignore: JSONObject, viewModel: MainViewModel) {
    val jsonObject = viewModel.getSelectedErrorDetails()
    var listOfSymptoms = ArrayList<String>()
    var listOfSolutions = ArrayList<String>()
    for (i in 0 until jsonObject.getJSONArray("symptoms").length()) {
        listOfSymptoms.add(jsonObject.getJSONArray("symptoms").get(i).toString())
    }
    for (i in 0 until jsonObject.getJSONArray("solutions").length()) {
        listOfSolutions.add(jsonObject.getJSONArray("solutions").get(i).toString())
    }

    LazyColumn(modifier = modifier.padding(20.dp)) {
        item() {
            Column(modifier = Modifier) {
                Text(
                    modifier = Modifier,
                    text = "Engine Errors",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                    )
                )

                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row {
                        Column {
                            Text(
                                text = "Error Code",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = jsonObject.get("dtc").toString(),
                                style = TextStyle(
                                    color = Color(0xFFF37900),
                                    fontSize = 18.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = modifier
                                .background(
                                    color = Color(0xFFF37900),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(10.dp)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = jsonObject.get("severity").toString(),
                                style = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier,
                    text = if (listOfSolutions.size > 0) {
                        listOfSolutions.get(0).toString()
                    } else {
                        "No Solutions Found"
                    },
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.recomandation),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Recommendation",
                                maxLines = 1,
                                style = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(Font(R.font.hankengrotesk_medium))
                                )
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                modifier = Modifier,
                                text = when (jsonObject.get("severity").toString()) {
                                    "Severe" -> {
                                        "Please contact service centre immediately or Book RSA"
                                    }

                                    "High" -> {
                                        "Contact service centre or reach service centre within the next 10 to 20 days"
                                    }

                                    "Low" -> {
                                        "Register the complaint at the time of periodic service"
                                    }

                                    else -> {
                                        "Nothing to worry"
                                    }
                                },
                                maxLines = 2,
                                style = TextStyle(
                                    color = Color(0xFFF37900),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Start,
                                    fontFamily = FontFamily(Font(R.font.hankengrotesk_regular))
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
                ) {
                    Column() {
                        Row(
                            modifier = Modifier.padding(all = 10.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.module),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "Module",
                                    maxLines = 1,
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(Font(R.font.hankengrotesk_medium))
                                    )
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    modifier = Modifier,
                                    text = jsonObject.get("module").toString(),
                                    maxLines = 1,
                                    style = TextStyle(
                                        color = Color(0xFFF34A443),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = Color(0xFFC6C2C2))
                        )

                        Row(
                            modifier = Modifier.padding(all = 10.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sub_module),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = "Status",
                                    maxLines = 1,
                                    style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(Font(R.font.hankengrotesk_medium))
                                    )
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Text(
                                    modifier = Modifier,
                                    text = jsonObject.get("status").toString(),
                                    maxLines = 1,
                                    style = TextStyle(
                                        color = Color(0xFFF34A443),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(Font(R.font.hankengrotesk_regular))
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorExplanation(modifier: Modifier, ignore: JSONObject, viewModel: MainViewModel) {
    var jsonObject = viewModel.selectedErrorJSONObject
    val causes = ArrayList<String>()
    for (i in 0 until jsonObject.getJSONArray("causes").length()) {
        causes.add(jsonObject.getJSONArray("causes").getString(i))
    }
    Column(modifier = modifier.padding(20.dp)) {
        Text(
            modifier = Modifier,
            text = "Description",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier,
            text = jsonObject.get("descriptions").toString().replace("[","").replace("]" ,""),
            style = TextStyle(
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.hankengrotesk_regular))
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier,
            text = "Causes",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        LazyColumn() {
            itemsIndexed(causes) { int, causes ->
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "$int . $causes",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.hankengrotesk_regular))
                    )
                )
            }
        }
    }
}

@Composable
fun jSONObjectFromPrevious(): String? {
    val context = LocalContext.current
    var json: String? = null
    json = try {
        val `is`: InputStream = context.getAssets().open("faults.json")
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, charset("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}
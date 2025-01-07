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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
            Color(0xFF040A1B),
            Color(0xFF040A1B)
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "${viewModel.selectedErrorName} Errors",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                )
            )

            ErrorCounts(
                modifier = Modifier.padding(10.dp),
                image = R.drawable.critical,
                name = "Critical",
                count = viewModel.criticalSeverity
            )
            ErrorCounts(
                modifier = Modifier.padding(10.dp),
                image = R.drawable.high,
                name = "High",
                count = viewModel.highSeverity
            )
            ErrorCounts(
                modifier = Modifier.padding(10.dp),
                image = R.drawable.low,
                name = "Low",
                count = viewModel.lowSeverity
            )
        }
        Spacer(modifier = Modifier.size(10.dp))

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(id = R.drawable.engine),
                    contentDescription = ""
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .width(150.dp)
                        .shadow(
                            1.dp,
                            spotColor = Color(0xFF20368B),
                            shape = RoundedCornerShape(
                                bottomStart = 120.dp,
                                bottomEnd = 100.dp,
                                topStart = 100.dp,
                                topEnd = 100.dp
                            )
                        )
                )
            }
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

    val backgroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF000000),
            Color(0xFF76ADFF).copy(alpha = 0.2f)
        )
    )

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
            .background(brush = backgroundGradient, shape = RoundedCornerShape(10.dp))
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    text = meaning,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.hankengrotesk_extrabold))
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                if (listOfFaults.get(errorIndex).getJSONObject(name).get("severity")
                        .toString() == "Critical"
                ) {
                    Image(
                        modifier = Modifier
                            .size(12.dp),
                        painter = painterResource(id = R.drawable.critical),
                        contentDescription = ""
                    )
                } else if (listOfFaults.get(errorIndex).getJSONObject(name).get("severity")
                        .toString() == "High"
                ) {
                    Image(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.high),
                        contentDescription = ""
                    )
                } else {
                    Image(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.low),
                        contentDescription = ""
                    )
                }

            }
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
fun ErrorDetails(modifier: Modifier, jsonObject: JSONObject, viewModel: MainViewModel) {
    val jsonObject = viewModel.getSelectedErrorDetails()
    var listOfSymptoms = ArrayList<String>()
    var listOfSolutions = ArrayList<String>()
    for (i in 0 until jsonObject.getJSONArray("symptoms").length()) {
        listOfSymptoms.add(jsonObject.getJSONArray("symptoms").get(i).toString())
    }
    for (i in 0 until jsonObject.getJSONArray("solutions").length()) {
        listOfSolutions.add(jsonObject.getJSONArray("solutions").get(i).toString())
    }

    val backgroundGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF000000),
            Color(0xFF76ADFF).copy(alpha = 0.2f)
        )
    )

    val nonSelectedGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5),
            Color(0xFF090F26).copy(alpha = 0f)
        )
    )

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
                        .background(brush = backgroundGradient, shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row {
                        Column(
                        ) {
                            Text(
                                text = "Error Code",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 8.sp
                                )
                            )
                            Text(
                                text = jsonObject.get("dtc").toString(),
                                style = TextStyle(
                                    color = if (jsonObject
                                            .get("severity")
                                            .toString() == "Critical"
                                    ) Color(0xFFD72D2D) else
                                        if (jsonObject.get("severity") == "Low") Color(
                                            0xFFCEB91C
                                        ) else Color(0xFFD7602D),
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.weight(2f))
                        Box(
                            modifier = modifier
                                .background(
                                    color = if (jsonObject
                                            .get("severity")
                                            .toString() == "Critical"
                                    ) Color(0xFFD72D2D) else
                                        if (jsonObject.get("severity") == "Low") Color(
                                            0xFFCEB91C
                                        ) else Color(0xFFD7602D),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (jsonObject
                                        .get("severity")
                                        .toString() == "Critical"
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .size(12.dp),
                                        painter = painterResource(id = R.drawable.critical),
                                        contentDescription = ""
                                    )
                                } else if (jsonObject.get("severity") == "Low"
                                ) {
                                    Image(
                                        modifier = Modifier.size(12.dp),
                                        painter = painterResource(id = R.drawable.low),
                                        contentDescription = ""
                                    )
                                } else {
                                    Image(
                                        modifier = Modifier.size(12.dp),
                                        painter = painterResource(id = R.drawable.high),
                                        contentDescription = ""
                                    )
                                }

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
                }
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .background(brush = backgroundGradient, shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(12.dp),
                                    painter = painterResource(id = R.drawable.recomandation),
                                    contentDescription = ""
                                )
                                Spacer(modifier = Modifier.size(10.dp))
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
                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            Box(modifier = Modifier) {
                                Row(
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(
                                        modifier = Modifier.size(12.dp),
                                        painter = painterResource(id = R.drawable.info),
                                        contentDescription = "Information"
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Text(
                                        modifier = Modifier.weight(2f),
                                        text = when (jsonObject.get("severity").toString()) {
                                            "Critical" -> {
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
                                        maxLines = 3,
                                        style = TextStyle(
                                            color = Color(0xFFF37900),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                            textAlign = TextAlign.Start,
                                            fontFamily = FontFamily(Font(R.font.hankengrotesk_regular))
                                        )
                                    )
                                    Spacer(modifier = Modifier.weight(0.5f))
                                    ClickableText(
                                        onClick = {

                                        },
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .background(
                                                brush =
                                                nonSelectedGradient,
                                                shape = CircleShape
                                            )
                                            .border(
                                                1.dp,
                                                color = Color.White.copy(alpha = 0.2f),
                                                shape = CircleShape
                                            )
                                            .padding(horizontal = 15.dp, vertical = 5.dp),
                                        text = AnnotatedString("Book RSA"),
                                        style = TextStyle(
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        item() {
            Text(
                text = jsonObject.get("descriptions").toString().replace("[","").replace("]",""),
                style = TextStyle(color = Color.White, fontSize = 8.sp)
            )
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
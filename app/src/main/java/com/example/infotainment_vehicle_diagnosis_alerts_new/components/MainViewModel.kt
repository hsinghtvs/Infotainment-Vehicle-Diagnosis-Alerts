package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.infotainment_vehicle_diagnosis_alerts_new.model.Severity
import org.json.JSONObject

class MainViewModel : ViewModel() {
    var listOfFaults: ArrayList<JSONObject> = arrayListOf()
    var selectedErrorJSONObject = JSONObject()
    var isScanningProgress by mutableStateOf(false)
    var isScanningDone by mutableStateOf(false)
    var listOfScanningDone = ArrayList<Int>()
    var scanningIndex by mutableStateOf(0)
    var scanningComponents = mutableListOf<String>()
    var severityHashMap by mutableStateOf(HashMap<String, HashMap<String, Severity>>())
    var criticalSeverity by mutableStateOf(0)
    var highSeverity by mutableStateOf(0)
    var lowSeverity by mutableStateOf(0)
    var selectedErrorName by mutableStateOf("")
    var selectedSeverity by mutableStateOf("")
    var closeApp by mutableStateOf(false)

    init {
        setScanningComponents()
    }

    private fun setScanningComponents() {
        scanningComponents.add("Engine")
        scanningComponents.add("Transmission")
        scanningComponents.add("ADM")
        scanningComponents.add("ABS")
        scanningComponents.add("EPS")
        scanningComponents.add("BCM")
        scanningComponents.add("Smart Key ")
        scanningComponents.add("CNG")
        scanningComponents.add("Instrument Cluster")
        scanningComponents.add("Airbag")
    }

    fun setName(faults: ArrayList<JSONObject>) {
        listOfFaults = faults
    }

    fun getName(): ArrayList<JSONObject> {
        return listOfFaults
    }

    fun setSelectedErrorDetails(details: JSONObject) {
        selectedErrorJSONObject = details
    }

    fun getSelectedErrorDetails(): JSONObject {
        return selectedErrorJSONObject
    }
}
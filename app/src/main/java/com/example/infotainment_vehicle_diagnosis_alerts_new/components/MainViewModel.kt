package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class MainViewModel : ViewModel() {
    var listOfFaults : ArrayList<JSONObject> = arrayListOf()
    var selectedErrorJSONObject = JSONObject()
    var isScanningProgress by mutableStateOf(false)
    var isScanningDone by mutableStateOf(false)
    var listOfScanning = ArrayList<String>()
    var listOfScanningDone = ArrayList<Int>()
    var scanningIndex by mutableStateOf(0)

    init {
        setListOfScanning()
    }

    private fun setListOfScanning(){
        listOfScanning.add("A/C")
        listOfScanning.add("Engine")
        listOfScanning.add("Battery")
        listOfScanning.add("Fuel level")
        listOfScanning.add("Clutch")
        listOfScanning.add("Vehicle Stability")
        listOfScanning.add("Engine")
        listOfScanning.add("Battery")
        listOfScanning.add("Fuel level")
        listOfScanning.add("Others")
    }

    fun setName(faults : ArrayList<JSONObject>){
        listOfFaults = faults
    }

    fun getName() : ArrayList<JSONObject>{
        return listOfFaults
    }

    fun setSelectedErrorDetails(details :JSONObject){
        selectedErrorJSONObject = details
    }

    fun getSelectedErrorDetails() : JSONObject{
        return selectedErrorJSONObject
    }
}
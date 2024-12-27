package com.example.infotainment_vehicle_diagnosis_alerts_new.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.json.JSONObject

class MainViewModel : ViewModel() {
    var listOfFaults : ArrayList<JSONObject> = arrayListOf()
    var selectedErrorJSONObject = JSONObject()

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
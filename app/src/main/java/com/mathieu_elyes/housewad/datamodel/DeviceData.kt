package com.mathieu_elyes.housewad.datamodel

data class DeviceData(
    val id: String,
    val type: String,
    val availableCommands: List<String>,
    var opening: Double? = null,
    var openingMode: Int? = null,
    var power: Int? = null
)
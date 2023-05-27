package com.example.ezlotest.api.dto

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    @SerializedName("Devices")
    val devices: List<DeviceDto>
)
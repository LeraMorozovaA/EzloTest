package com.example.ezlotest.api.dto

import com.google.gson.annotations.SerializedName

data class DeviceDto(
    @SerializedName("PK_Device") val pKDevice: Int,
    @SerializedName("MacAddress") val macAddress: String,
    @SerializedName("PK_DeviceType") val pKDeviceType: Int,
    @SerializedName("PK_DeviceSubType") val pKDeviceSubType: Int,
    @SerializedName("Firmware") val firmware: String,
    @SerializedName("Server_Device") val serverDevice: String,
    @SerializedName("Server_Event") val serverEvent: String,
    @SerializedName("Server_Account") val serverAccount: String,
    @SerializedName("InternalIP") val internalIP: String,
    @SerializedName("LastAliveReported") val lastAliveReported: String,
    @SerializedName("Platform") val platform: String,
    @SerializedName("PK_Account") val pKAccount: Int
)

package com.example.ezlotest.api.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeviceDto(
    @SerializedName("PK_Device")
    @Expose
    val pKDevice: Int,

    @SerializedName("MacAddress")
    @Expose
    val macAddress: String,

    @SerializedName("PK_DeviceType")
    @Expose
    val pKDeviceType: Int,

    @SerializedName("PK_DeviceSubType")
    @Expose
    val pKDeviceSubType: Int,

    @SerializedName("Firmware")
    @Expose
    val firmware: String,

    @SerializedName("Server_Device")
    @Expose
    val serverDevice: String,

    @SerializedName("Server_Event")
    @Expose
    val serverEvent: String,

    @SerializedName("Server_Account")
    @Expose
    val serverAccount: String,

    @SerializedName("InternalIP")
    @Expose
    val internalIP: String,

    @SerializedName("LastAliveReported")
    @Expose
    val lastAliveReported: String,

    @SerializedName("Platform")
    @Expose
    val platform: String,

    @SerializedName("PK_Account")
    @Expose
    val pKAccount: Int
)

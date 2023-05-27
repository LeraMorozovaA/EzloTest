package com.example.ezlotest.api.model

data class Device(
    val pKDevice: Int,
    val macAddress: String,
    val pKDeviceType: Int,
    val pKDeviceSubType: Int,
    val firmware: String,
    val serverDevice: String,
    val serverEvent: String,
    val serverAccount: String,
    val internalIP: String,
    val lastAliveReported: String,
    val platform: String,
    val pKAccount: Int
)

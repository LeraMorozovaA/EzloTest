package com.example.ezlotest.api.mapper

import com.example.ezlotest.api.dto.DeviceDto
import com.example.ezlotest.api.model.Device

fun DeviceDto.toModel() = Device(
    pKDevice = pKDevice,
    macAddress = macAddress,
    pKDeviceType = pKDeviceType,
    pKDeviceSubType = pKDeviceSubType,
    firmware = firmware,
    serverDevice = serverDevice,
    serverEvent = serverEvent,
    serverAccount = serverAccount,
    internalIP = internalIP,
    lastAliveReported = lastAliveReported,
    platform = platform,
    pKAccount = pKAccount
)
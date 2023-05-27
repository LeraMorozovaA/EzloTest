package com.example.ezlotest.api.mapper

import com.example.ezlotest.api.dto.DeviceDto
import com.example.ezlotest.api.model.Device
import com.example.ezlotest.data.model.DeviceEntity

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
    platform = Device.setupPlatform(platform),
    pKAccount = pKAccount
)

fun Device.toEntity() = DeviceEntity(
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
    platform = platform.value,
    pKAccount = pKAccount
)

fun DeviceEntity.toModel() = Device(
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
    platform = Device.setupPlatform(platform),
    pKAccount = pKAccount
)
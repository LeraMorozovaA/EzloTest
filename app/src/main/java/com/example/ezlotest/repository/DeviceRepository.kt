package com.example.ezlotest.repository

import com.example.ezlotest.api.model.Device

interface DeviceRepository {

    /**
     * Get device list from API.
     * @return The list sorted by pKDevice
     */
    suspend fun getDeviceList(): List<Device>

    /**
     * Delete selected device by pKDevice
     */
    suspend fun deleteDeviceByPK(pKDevice: Int)

    /**
     * @return Device found by pKDevice
     */
    suspend fun getDeviceByPK(pKDevice: Int): Device

    /**
     * Update device with new data
     */
    suspend fun updateDevice(device: Device)

    /**
     * Get user info
     * @return Pair where first - user image resource id, second - user name
     */
    fun getUserInfo(): Pair<Int, String>

}
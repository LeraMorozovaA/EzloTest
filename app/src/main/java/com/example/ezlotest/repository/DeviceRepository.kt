package com.example.ezlotest.repository

import com.example.ezlotest.api.ApiService
import com.example.ezlotest.api.mapper.toModel
import com.example.ezlotest.api.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceRepository(private val apiService: ApiService) {

    suspend fun getDeviceList(): List<Device> {
        return withContext(Dispatchers.IO) {
            apiService.getItemsList().devices.map { it.toModel() }
        }
    }
}
package com.example.ezlotest.repository

import android.content.Context
import com.example.ezlotest.R
import com.example.ezlotest.api.ApiService
import com.example.ezlotest.api.mapper.toEntity
import com.example.ezlotest.api.mapper.toModel
import com.example.ezlotest.api.model.Device
import com.example.ezlotest.data.dao.DeviceDao
import com.example.ezlotest.data.local.LocalStorageService
import com.example.ezlotest.ui.common.hasInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map

class DeviceRepository(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val deviceDao: DeviceDao,
    private val localStorage: LocalStorageService
    ) {

    val listFlow = deviceDao.getAll().map { it.map { it.toModel() } }

    suspend fun getDeviceList(): List<Device> {
        return if (hasInternetConnection(context)) {
            val list = fetchDeviceList()
            deviceDao.insertAll(list.map { it.toEntity() })
            list
        } else {
            getDeviceListFromDB()
        }
    }

    suspend fun deleteDeviceByPK(pKDevice: Int) {
        deviceDao.deleteDeviceByPK(pKDevice)
        val deletedPKSet = localStorage.getDeletedDevicePKSet().toMutableSet()
            .apply { add(pKDevice.toString()) }
        localStorage.setDeletedDevicePKSet(deletedPKSet)
    }

    suspend fun getDeviceByPK(pKDevice: Int): Device {
        return deviceDao.getDeviceByPK(pKDevice).toModel()
    }

    private fun getDeviceListFromDB(): List<Device> {
        return deviceDao.getDeviceList().map { it.toModel() }.sortedBy { it.pKDevice }
    }

    private suspend fun fetchDeviceList(): List<Device> {
        return apiService.getItemsList().devices
            .map { it.toModel() }
            .filter { !isPKDeviceDeletedByUser(it.pKDevice)  }
            .sortedBy { it.pKDevice }
    }

    private suspend fun isPKDeviceDeletedByUser(pKDevice: Int): Boolean {
        val item = localStorage.getDeletedDevicePKSet().map { it.toInt() }
            .firstOrNull { pk -> pk == pKDevice }
        return item != null
    }

    fun getUserInfo(): Pair<Int, String> {
        return R.drawable.ic_photo to USER_NAME
    }

    companion object {
        private const val USER_NAME = "Valeriia Morozova"
    }
}
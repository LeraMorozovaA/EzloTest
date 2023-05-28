package com.example.ezlotest.api

import android.content.Context
import com.example.ezlotest.api.mapper.toEntity
import com.example.ezlotest.api.mapper.toModel
import com.example.ezlotest.api.model.Device
import com.example.ezlotest.data.dao.DeviceDao
import com.example.ezlotest.data.local.LocalStorageService
import com.example.ezlotest.repository.DeviceRepository
import com.example.ezlotest.ui.common.Constants.USER_NAME
import com.example.ezlotest.ui.common.Constants.USER_PHOTO
import com.example.ezlotest.ui.common.hasInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DeviceRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val deviceDao: DeviceDao,
    private val localStorage: LocalStorageService
): DeviceRepository {

    val listFlow = deviceDao.getDeviceListFlow().map { list -> list.map { entity -> entity.toModel() } }

    override suspend fun getDeviceList(): List<Device> = withContext(Dispatchers.IO) {
        return@withContext if (hasInternetConnection(context)) {
            val list = fetchDeviceList()
                .apply { updateDeviceTitles(this) }
            deviceDao.insertAll(list.map { it.toEntity() })
            list
        } else {
            getDeviceListFromDB()
        }
    }

    override suspend fun deleteDeviceByPK(pKDevice: Int) = withContext(Dispatchers.IO) {
        deviceDao.deleteDeviceByPK(pKDevice)
        val deletedPKSet = localStorage.getDeletedDevicePKSet().toMutableSet()
            .apply { add(pKDevice.toString()) }
        localStorage.setDeletedDevicePKSet(deletedPKSet)
    }

    override suspend fun getDeviceByPK(pKDevice: Int): Device {
        return withContext(Dispatchers.IO) {
            deviceDao.getDeviceByPK(pKDevice).toModel()
        }
    }

    override suspend fun updateDevice(device: Device) = withContext(Dispatchers.IO){
        deviceDao.updateDevice(device.toEntity())
    }

    override fun getUserInfo(): Pair<Int, String> {
        return USER_PHOTO to USER_NAME
    }

    private suspend fun getDeviceListFromDB(): List<Device> {
        return withContext(Dispatchers.IO) {
            deviceDao.getDeviceList().map { it.toModel() }.sortedBy { it.pKDevice }
        }
    }

    private suspend fun fetchDeviceList(): List<Device> {
        return withContext(Dispatchers.IO) {
            apiService.getItemsList().devices
                .map { it.toModel() }
                .filter { !isPKDeviceDeletedByUser(it.pKDevice) }
                .sortedBy { it.pKDevice }
        }
    }

    private suspend fun isPKDeviceDeletedByUser(pKDevice: Int): Boolean = withContext(Dispatchers.IO){
        val item = localStorage.getDeletedDevicePKSet().map { it.toInt() }
            .firstOrNull { pk -> pk == pKDevice }
        return@withContext item != null
    }

    private fun updateDeviceTitles(list: List<Device>): List<Device> {
        val oldListWithDeviceTitles = deviceDao.getDeviceList().filter { !it.deviceTitle.isNullOrBlank() }
        oldListWithDeviceTitles.forEach { device ->
            list.find { device.pKDevice == it.pKDevice }?.deviceTitle = device.deviceTitle
        }
        return list
    }
}
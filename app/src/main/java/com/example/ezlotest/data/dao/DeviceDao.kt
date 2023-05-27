package com.example.ezlotest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ezlotest.data.model.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices")
    fun getDeviceList(): List<DeviceEntity>

    @Query("SELECT * FROM devices")
    fun getAll(): Flow<List<DeviceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<DeviceEntity>)

    @Query("SELECT * FROM devices WHERE pKDevice = :pKDevice")
    fun getDeviceByPK(pKDevice: Int): DeviceEntity

    @Query("DELETE FROM devices WHERE pKDevice=:pKDevice")
    suspend fun deleteDeviceByPK(pKDevice: Int)

}
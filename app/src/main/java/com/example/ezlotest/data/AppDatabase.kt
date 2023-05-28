package com.example.ezlotest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ezlotest.data.dao.DeviceDao
import com.example.ezlotest.data.model.DeviceEntity

@Database(entities = [DeviceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}
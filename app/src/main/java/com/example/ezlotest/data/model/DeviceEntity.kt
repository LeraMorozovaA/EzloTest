package com.example.ezlotest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = "pKDevice") val pKDevice: Int,
    @ColumnInfo(name = "macAddress") val macAddress: String,
    @ColumnInfo(name = "pKDeviceType") val pKDeviceType: Int,
    @ColumnInfo(name = "pKDeviceSubType") val pKDeviceSubType: Int,
    @ColumnInfo(name = "firmware") val firmware: String,
    @ColumnInfo(name = "serverDevice") val serverDevice: String,
    @ColumnInfo(name = "serverEvent") val serverEvent: String,
    @ColumnInfo(name = "serverAccount") val serverAccount: String,
    @ColumnInfo(name = "internalIP") val internalIP: String,
    @ColumnInfo(name = "lastAliveReported") val lastAliveReported: String,
    @ColumnInfo(name = "platform") val platform: String,
    @ColumnInfo(name = "pKAccount") val pKAccount: Int
)
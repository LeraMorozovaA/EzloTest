package com.example.ezlotest.api.model

import android.content.Context
import com.example.ezlotest.R

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
    val platform: Platform,
    val pKAccount: Int,
    var deviceTitle: String?
) {

    fun getDeviceTitle(context: Context, indexInList: Int): String {
        return if (deviceTitle.isNullOrEmpty()) {
            context.getString(R.string.default_device_title, indexInList + 1)
        } else {
            deviceTitle!!
        }
    }

    companion object {
        fun setupPlatform(platform: String): Platform {
            if (platform.isEmpty()) return Platform.UNKNOWN
            for (p in Platform.values()) {
                if (p.value == platform) {
                    return p
                }
            }
            return Platform.UNKNOWN
        }
    }
}

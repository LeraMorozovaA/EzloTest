package com.example.ezlotest.ui.viewstate

import com.example.ezlotest.api.model.Device

class DeviceListViewState: UserViewState() {
    var list: List<Device> = listOf()
}
package com.example.ezlotest.ui.viewstate

import com.example.ezlotest.api.model.Device
import com.example.ezlotest.ui.details.ScreenMode

class DeviceDetailsViewState: UserViewState() {
    var device: Device? = null
    var screenMode = ScreenMode.VIEW
}
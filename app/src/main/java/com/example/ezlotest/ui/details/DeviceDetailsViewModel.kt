package com.example.ezlotest.ui.details

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezlotest.api.model.Device
import com.example.ezlotest.repository.DeviceRepository
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.list.DeviceListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val repository: DeviceRepository
): ViewModel() {

    val viewState = MutableStateFlow<ViewState<DeviceDetailsViewState>>(ViewState.Idle)
    private val state = DeviceDetailsViewState()

    fun getDeviceInfo(pKDevice: Int) = viewModelScope.launch {
        val userInfo = repository.getUserInfo()
        val deviceInfo = repository.getDeviceByPK(pKDevice)

        state.apply {
            photo = userInfo.first
            name = userInfo.second
            device = deviceInfo
        }
        viewState.value = ViewState.Data(state)
    }
}

class DeviceDetailsViewState {
    var photo: Int = 0
    var name: String = ""
    var device: Device? = null
}
package com.example.ezlotest.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezlotest.api.DeviceRepositoryImpl
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.viewstate.DeviceDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    private val repository: DeviceRepositoryImpl
): ViewModel() {

    val viewState = MutableStateFlow<ViewState<DeviceDetailsViewState>>(ViewState.Idle)
    private val state = DeviceDetailsViewState()

    fun getDeviceInfo(pKDevice: Int, mode: ScreenMode) = viewModelScope.launch {
        try {
            val userInfo = repository.getUserInfo()
            val deviceInfo = repository.getDeviceByPK(pKDevice)

            state.apply {
                photo = userInfo.first
                name = userInfo.second
                device = deviceInfo
                screenMode = mode
            }
            viewState.value = ViewState.Data(state)
        } catch (e: Exception) {
            viewState.value = ViewState.Error(e)
        }
    }

    fun saveDeviceTitle(newTitle: String) = viewModelScope.launch {
        if (state.screenMode != ScreenMode.EDIT || newTitle == state.device?.deviceTitle) return@launch

        val device = state.device.apply { this?.deviceTitle = newTitle } ?: return@launch
        viewState.value = ViewState.Loading
        try {
            repository.updateDevice(device)
            viewState.value = ViewState.Success
        } catch (e: Exception) {
            viewState.value = ViewState.Error(e)
        }
    }
}
package com.example.ezlotest.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezlotest.api.DeviceRepositoryImpl
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.viewstate.DeviceListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    private val repository: DeviceRepositoryImpl
) : ViewModel() {

    val viewState = MutableStateFlow<ViewState<DeviceListViewState>>(ViewState.Idle)
    private val state = DeviceListViewState()

    init {
        viewModelScope.launch {
            loadData()
        }

        repository.listFlow.drop(1).onEach {
            state.list = it
            viewState.value = ViewState.Data(state)
        }.launchIn(viewModelScope)
    }

    fun deleteSelectedDevice(pKDevice: Int) = viewModelScope.launch {
        viewState.value = ViewState.Loading
        try {
            repository.deleteDeviceByPK(pKDevice)
        } catch (e: Exception) {
            viewState.value = ViewState.Error(e)
        }
    }

    private suspend fun loadData() = withContext(Dispatchers.IO) {
        viewState.value = ViewState.Loading
        try {
            val userInfo = repository.getUserInfo()
            val itemsList = repository.getDeviceList()

            state.apply {
                photo = userInfo.first
                name = userInfo.second
                list = itemsList
            }

            viewState.value = ViewState.Data(state)
        } catch (e: Exception) {
            viewState.value = ViewState.Error(e)
        }
    }

}
package com.example.ezlotest.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ezlotest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceListFragment: Fragment(R.layout.fragment_device_list) {

    private val viewModel: DeviceListViewModel by viewModels()
}
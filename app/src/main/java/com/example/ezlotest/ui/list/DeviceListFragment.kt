package com.example.ezlotest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ezlotest.R
import com.example.ezlotest.databinding.FragmentDeviceListBinding
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.common.showAlert
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DeviceListFragment: Fragment(R.layout.fragment_device_list) {

    private val viewModel: DeviceListViewModel by viewModels()
    private var adapter: DeviceAdapter? = null
    private var headerAdapter: HeaderAdapter? = null

    private lateinit var binding: FragmentDeviceListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserving()
    }

    private fun setupObserving() {
        viewModel.viewState.onEach { state ->
            binding.progress.isVisible = state is ViewState.Loading

            when(state){
                is ViewState.Data -> setData(state.data)
                is ViewState.Error -> showError()
                else -> return@onEach
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupRecyclerView() {
        adapter = DeviceAdapter(
            onClick = { pkDevice ->  },
            onLongClick = { pkDevice -> showDeleteDeviceAlert(pkDevice) },
            onEditClick = { pkDevice -> }
        )
        headerAdapter = HeaderAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ConcatAdapter(headerAdapter, adapter)
    }

    private fun setData(state: DeviceListViewState) {
        headerAdapter?.data = state.photo to state.name
        adapter?.data = state.list

        binding.recyclerView.isVisible = state.list.isNotEmpty()
        binding.txtInfo.isVisible = state.list.isEmpty()

        binding.txtInfo.text = getString(R.string.empty_list_title)
    }

    private fun showDeleteDeviceAlert(pkDevice: Int) {
        MaterialAlertDialogBuilder(requireContext()).showAlert(
            message = getString(R.string.alert_message),
            pTextButton = getString(R.string.button_ok),
            nTextButton = getString(R.string.button_cancel),
            onClick = {
                viewModel.deleteSelectedDevice(pkDevice)
            }
        )
    }

    private fun showError() {
        binding.recyclerView.visibility = View.GONE
        binding.txtInfo.visibility = View.VISIBLE

        binding.txtInfo.text = getString(R.string.error_title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        headerAdapter = null
    }
}
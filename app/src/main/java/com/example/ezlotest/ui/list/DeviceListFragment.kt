package com.example.ezlotest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ezlotest.R
import com.example.ezlotest.databinding.FragmentDeviceListBinding
import com.example.ezlotest.ui.adapters.DeviceAdapter
import com.example.ezlotest.ui.adapters.HeaderAdapter
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.common.showAlert
import com.example.ezlotest.ui.details.DeviceDetailsFragment.Companion.ARG_DEVICE_PK
import com.example.ezlotest.ui.details.DeviceDetailsFragment.Companion.ARG_INDEX
import com.example.ezlotest.ui.details.DeviceDetailsFragment.Companion.ARG_SCREEN_MODE
import com.example.ezlotest.ui.details.ScreenMode
import com.example.ezlotest.ui.viewstate.DeviceListViewState
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
            onClick = { info ->  navigateToDeviceDetailsFragment(info.first, screenMode = ScreenMode.VIEW, info.second) },
            onLongClick = { pkDevice -> showDeleteDeviceAlert(pkDevice) },
            onEditClick = { info -> navigateToDeviceDetailsFragment(info.first, screenMode = ScreenMode.EDIT, info.second) }
        )
        headerAdapter = HeaderAdapter()

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ConcatAdapter(headerAdapter, adapter)
    }

    private fun navigateToDeviceDetailsFragment(pkDevice: Int, screenMode: ScreenMode, index: Int) {
        val bundle = bundleOf(
            ARG_DEVICE_PK to pkDevice,
            ARG_SCREEN_MODE to screenMode,
            ARG_INDEX to index
        )
        findNavController().navigate(R.id.action_deviceListFragment_to_deviceDetailsFragment, bundle)
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
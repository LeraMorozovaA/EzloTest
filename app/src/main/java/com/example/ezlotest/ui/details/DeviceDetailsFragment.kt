package com.example.ezlotest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ezlotest.R
import com.example.ezlotest.databinding.FragmentDeviseDetailsBinding
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.common.showAlert
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DeviceDetailsFragment: Fragment(R.layout.fragment_devise_details) {

    private val viewModel: DeviceDetailsViewModel by viewModels()

    private lateinit var binding: FragmentDeviseDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pKDevice = requireArguments().getInt(ARG_DEVICE_PK)
        viewModel.getDeviceInfo(pKDevice)

        setupObserving()
    }

    private fun setupObserving() {
        viewModel.viewState.onEach { state ->
            when(state){
                is ViewState.Data -> setData(state.data)
                is ViewState.Error -> showError()
                else -> return@onEach
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setData(state: DeviceDetailsViewState) {
        val device = state.device ?: return

        binding.user.ivPhoto.setImageResource(state.photo)
        binding.user.tvName.text = state.name

        binding.ivDevice.setImageResource(device.platform.imageRes)

        binding.txtSn.text = getString(R.string.sn_title, device.pKDevice.toString())
        binding.txtMacAddress.text = getString(R.string.mac_address, device.macAddress)
        binding.txtFirmware.text = getString(R.string.firmware, device.firmware)
        binding.txtModel.text = getString(R.string.model, device.platform.value)
    }

    private fun showError() {
        MaterialAlertDialogBuilder(requireContext()).showAlert(
            message = getString(R.string.error_title),
            pTextButton = getString(R.string.button_ok),
            onClick = { findNavController().popBackStack() }
        )
    }

    companion object {
        const val ARG_DEVICE_PK = "arg_device_pk"
        const val ARG_EDIT_MODE = "arg_edit_mode"
    }
}
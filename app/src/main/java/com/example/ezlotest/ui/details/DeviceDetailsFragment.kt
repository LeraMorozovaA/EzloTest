package com.example.ezlotest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ezlotest.R
import com.example.ezlotest.databinding.FragmentDeviceDetailsBinding
import com.example.ezlotest.ui.common.ViewState
import com.example.ezlotest.ui.common.showAlert
import com.example.ezlotest.ui.common.showKeyboard
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DeviceDetailsFragment: Fragment(R.layout.fragment_device_details) {

    private val viewModel: DeviceDetailsViewModel by viewModels()

    private lateinit var binding: FragmentDeviceDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pKDevice = requireArguments().getInt(ARG_DEVICE_PK)
        val screenMode = requireArguments().getSerializable(ARG_SCREEN_MODE) as ScreenMode
        viewModel.getDeviceInfo(pKDevice, screenMode)

        setupObserving()
        setupListeners()
    }

    private fun setupObserving() {
        viewModel.viewState.onEach { state ->
            when(state){
                is ViewState.Data -> setData(state.data)
                is ViewState.Error -> showError()
                is ViewState.Success -> findNavController().popBackStack()
                else -> return@onEach
            }

            binding.progress.isVisible = state is ViewState.Loading
            binding.btnSave.isEnabled = state is ViewState.Data
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val newTitle: String = binding.editText.text.toString()
            if (newTitle.isEmpty()) return@setOnClickListener
            viewModel.saveDeviceTitle(newTitle)
        }
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

        binding.txtTitle.isVisible = state.screenMode == ScreenMode.VIEW
        binding.editText.isVisible = state.screenMode == ScreenMode.EDIT
        binding.btnSave.isVisible = state.screenMode == ScreenMode.EDIT

        val deviceTitle = device.getDeviceTitle(requireContext())
        if (state.screenMode == ScreenMode.VIEW) {
            binding.txtTitle.text = deviceTitle
        } else {
            binding.editText.setText(deviceTitle)
            binding.editText.apply {
                requestFocus()
                showKeyboard()
            }
        }
    }

    private fun showError() {
        MaterialAlertDialogBuilder(requireContext()).showAlert(
            message = getString(R.string.error_title),
            pTextButton = getString(R.string.button_ok),
            onClick = { findNavController().popBackStack() }
        )
    }

    override fun onPause() {
        super.onPause()
        binding.editText.clearFocus()
    }

    companion object {
        const val ARG_DEVICE_PK = "arg_device_pk"
        const val ARG_SCREEN_MODE = "arg_screen_mode"
    }
}
package com.example.ezlotest.ui.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ezlotest.R
import com.example.ezlotest.api.model.Device
import com.example.ezlotest.api.model.Platform
import com.example.ezlotest.databinding.ItemDeviceBinding

@SuppressLint("NotifyDataSetChanged")
class DeviceAdapter(
    private val onClick: (Int) -> Unit,
    private val onEditClick: (Int) -> Unit,
    private val onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    var data: List<Device> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int =  data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding, onClick, onEditClick, onLongClick)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(data[position])
    }


    @SuppressLint("NotifyDataSetChanged")
    inner class DeviceViewHolder(
        private val binding: ItemDeviceBinding,
        private val onClick: (Int) -> Unit,
        private val onEditClick: (Int) -> Unit,
        private val onLongClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var device: Device

        init {
            binding.container.setOnClickListener { onClick.invoke(device.pKDevice) }
            binding.btnEdit.setOnClickListener { onEditClick.invoke(device.pKDevice) }
            binding.container.setOnLongClickListener {
                onLongClick.invoke(device.pKDevice)
                return@setOnLongClickListener true
            }
        }

        fun bind(item: Device?) {
            device = item ?: return

            binding.txtTitle.text = "HOME NUMBER 1"
            binding.txtSn.text = binding.root.context.getString(R.string.sn_title, item.pKDevice.toString())
            binding.ivDevice.setImageResource(item.platform.imageRes)
        }
    }
}
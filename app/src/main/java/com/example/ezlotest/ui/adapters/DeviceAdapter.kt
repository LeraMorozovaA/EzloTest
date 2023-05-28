package com.example.ezlotest.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ezlotest.R
import com.example.ezlotest.api.model.Device
import com.example.ezlotest.databinding.ItemDeviceBinding

@SuppressLint("NotifyDataSetChanged")
class DeviceAdapter(
    private val onClick: (Pair<Int, Int>) -> Unit,
    private val onEditClick: (Pair<Int, Int>) -> Unit,
    private val onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    var data: List<Device> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding, onClick, onEditClick, onLongClick)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class DeviceViewHolder(
        private val binding: ItemDeviceBinding,
        private val onClick: (Pair<Int, Int>) -> Unit,
        private val onEditClick: (Pair<Int, Int>) -> Unit,
        private val onLongClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var device: Device

        init {
            binding.container.setOnClickListener {
                onClick.invoke(device.pKDevice to data.indexOf(device))
            }
            binding.btnDetails.setOnClickListener {
                onClick.invoke(device.pKDevice to data.indexOf(device))
            }
            binding.btnEdit.setOnClickListener {
                onEditClick.invoke(device.pKDevice to data.indexOf(device))
            }
            binding.container.setOnLongClickListener {
                onLongClick.invoke(device.pKDevice)
                return@setOnLongClickListener true
            }
        }

        fun bind(item: Device?, index: Int) {
            device = item ?: return

            binding.txtTitle.text = item.getDeviceTitle(binding.root.context, index)
            binding.txtSn.text = binding.root.context.getString(R.string.sn_title, item.pKDevice.toString())
            binding.ivDevice.setImageResource(item.platform.imageRes)
        }
    }
}
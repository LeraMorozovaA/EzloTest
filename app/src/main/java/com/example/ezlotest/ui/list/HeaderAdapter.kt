package com.example.ezlotest.ui.list

import android.annotation.SuppressLint
import android.os.health.HealthStats
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ezlotest.databinding.LayoutUserInfoBinding

@SuppressLint("NotifyDataSetChanged")
class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var data: Pair<Int, String> = Pair(0, "")
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderViewHolder {
        val binding = LayoutUserInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderAdapter.HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class HeaderViewHolder(private val binding: LayoutUserInfoBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind() {
            binding.ivPhoto.setImageResource(data.first)
            binding.tvName.text = data.second
        }
    }
}
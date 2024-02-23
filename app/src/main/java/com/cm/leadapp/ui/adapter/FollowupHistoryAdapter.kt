package com.cm.leadapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cm.kbslead.databinding.ItemFollowupHistoryBinding
import com.cm.leadapp.data.responsemodel.FollowUpHistory

class FollowupHistoryAdapter(
    private val values: List<FollowUpHistory>
) : RecyclerView.Adapter<FollowupHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFollowupHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.apply {
            with(item) {
                tvFollowupDate.text = date
                tvType.text = type
                tvRemarks.text = remark
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemFollowupHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvFollowupDate: TextView = binding.tvFollowupDate
        val tvType: TextView = binding.tvType
        val tvRemarks: TextView = binding.tvRemarks
    }
}
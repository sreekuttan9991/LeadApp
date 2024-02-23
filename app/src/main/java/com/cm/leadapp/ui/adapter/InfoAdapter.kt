package com.cm.leadapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cm.kbslead.databinding.ItemInfoBinding
import com.cm.leadapp.data.uimodel.Info

class InfoAdapter(
    private val values: ArrayList<Info>
) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemInfoBinding.inflate(
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
                tvName.text = name
                tvValue.text = value
            }

        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvName: TextView = binding.tvName
        val tvValue: TextView = binding.tvValue
    }

}
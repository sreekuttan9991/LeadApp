package com.cm.leadapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cm.leadapp.data.uimodel.Statistics
import com.cm.leadapp.databinding.ItemStatisticsBinding
import com.cm.leadapp.util.ResourceUtil

class StatisticsAdapter(private val context: Context, private val mList: ArrayList<Statistics>) :
    RecyclerView.Adapter<StatisticsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ItemStatisticsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = mList[position]
        holder.apply {

            titleText.text = data.title
            valueText.text = data.value
            cardView.setCardBackgroundColor(ResourceUtil.getRandomColor(context))
        }
    }

    class MyViewHolder(val binding: ItemStatisticsBinding) : RecyclerView.ViewHolder(binding.root) {

        val titleText = binding.tvTitle
        val valueText = binding.tvValue
        val cardView = binding.cardStats
    }
}
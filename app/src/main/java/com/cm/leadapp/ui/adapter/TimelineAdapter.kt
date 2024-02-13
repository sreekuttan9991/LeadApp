package com.cm.leadapp.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cm.leadapp.data.responsemodel.TimelineData
import com.cm.leadapp.databinding.ItemTimelineBinding

class TimelineAdapter(private val mList: ArrayList<TimelineData>) :
    RecyclerView.Adapter<TimelineAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(ItemTimelineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = mList[position]
holder.apply {

    tvDate.text = data.date
    tvGroupName.text = data.groupName
    tvMessage.text = getHtml(data.message!!)

}
    }

    class MyViewHolder(val binding: ItemTimelineBinding):RecyclerView.ViewHolder(binding.root) {

        val tvDate = binding.tvDate
        val tvGroupName = binding.tvGroupName
        val tvMessage = binding.tvMessage
    }

    private fun getHtml(htmlBody: String): String {
        return Html.fromHtml(htmlBody, Html.FROM_HTML_MODE_LEGACY).toString()
    }

}
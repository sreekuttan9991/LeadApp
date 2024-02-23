package com.cm.leadapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.kbslead.databinding.ItemClosedLeadsBinding
import com.cm.leadapp.data.responsemodel.ClosedLeadData
import com.cm.leadapp.util.OnClosedLeadsItemClickListener
import com.cm.leadapp.util.firstCapital

class ClosedLeadsAdapter(private val onClosedLeadsItemClickListener: OnClosedLeadsItemClickListener) :
    PagingDataAdapter<ClosedLeadData, ClosedLeadsAdapter.ClosedLeadsViewHolder>(COMPARATOR) {

    class ClosedLeadsViewHolder(
        val binding: ItemClosedLeadsBinding,
        private val itemClickListener: OnClosedLeadsItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {

        private val txtClientName = binding.tvClient
        private val imgTimeLine = binding.ivTimeline
        private val imgView = binding.ivView
        private val txtTouchDate = binding.tvTime
        private var closedLeadData: ClosedLeadData? = null

        fun bind(item: ClosedLeadData?) {
            item?.let {
                closedLeadData = it
                txtClientName.text = it.client?.firstCapital()
                txtTouchDate.text = it.touchDate
            }
            imgTimeLine.setOnClickListener(this)
            imgView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener.onItemClick(closedLeadData, view)
        }
    }

    override fun onBindViewHolder(holder: ClosedLeadsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClosedLeadsViewHolder {
        return ClosedLeadsViewHolder(
            ItemClosedLeadsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClosedLeadsItemClickListener
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ClosedLeadData>() {
            override fun areItemsTheSame(
                oldItem: ClosedLeadData,
                newItem: ClosedLeadData
            ): Boolean {
                return oldItem.leadId == newItem.leadId
            }

            override fun areContentsTheSame(
                oldItem: ClosedLeadData,
                newItem: ClosedLeadData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

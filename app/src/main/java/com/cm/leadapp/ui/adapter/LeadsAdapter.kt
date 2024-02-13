package com.cm.leadapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.leadapp.data.responsemodel.LeadData
import com.cm.leadapp.databinding.ItemLeadsBinding
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.OnLeadsItemClickListener

class LeadsAdapter(private val leadsItemClickListener: OnLeadsItemClickListener) :
    PagingDataAdapter<LeadData, LeadsAdapter.LeadsViewHolder>(COMPARATOR) {

    class LeadsViewHolder(
        val binding: ItemLeadsBinding,
        val itemClickListener: OnLeadsItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {

        private val txtClientName = binding.tvClient
        private val imgWhatsapp = binding.ivWhatsapp
        private val imgCall = binding.ivCall
        private val txtProduct = binding.tvProduct
        private val txtStatus = binding.tvStatus
        private val txtTouchDate = binding.tvTouchDate
        private val txtCustomerCategory = binding.tvCategory
        private val imgDown = binding.ivDown
        private val imgTrash = binding.ivTrash
        private val imgChangeStatus = binding.ivEdit
        private val imgChangeAgent = binding.ivChangeAgent
        private val imgAddFollowup = binding.ivAddFollowup
        private val imgTimeLine = binding.ivTimeline
        private val imgView = binding.ivView
        private val cardMain = binding.cardMain
        private val cardActions = binding.cardActions
        private var leadData: LeadData? = null

        fun bind(item: LeadData?) {
            item?.let {
                leadData = it
                txtClientName.text = it.client
                it.product?.let { product ->
                    txtProduct.text = product
                }
                txtCustomerCategory.text = it.customerCategory
                txtTouchDate.text = it.touchDate
                GenUtils.setStatus(txtStatus, it.status)
                if(it.isDeleted.equals("1")){
                    cardMain.alpha = 0.5f
                    cardActions.alpha = 0.5f
                    imgTrash.visibility = View.GONE
                }
            }

            imgCall.setOnClickListener(this)
            imgWhatsapp.setOnClickListener(this)
            imgChangeStatus.setOnClickListener(this)
            imgAddFollowup.setOnClickListener(this)
            imgChangeAgent.setOnClickListener(this)
            imgTrash.setOnClickListener(this)
            imgTimeLine.setOnClickListener(this)
            imgView.setOnClickListener(this)

            imgDown.setOnClickListener {
                if (it.rotation == 0f) {
                    it.rotation = 180f
                    cardActions.visibility = View.VISIBLE
                } else {
                    it.rotation = 0f
                    cardActions.visibility = View.GONE
                }
            }
        }

        override fun onClick(view: View?) {
            itemClickListener.onItemClick(leadData, view)
        }
    }

    override fun onBindViewHolder(holder: LeadsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadsViewHolder {
        return LeadsViewHolder(
            ItemLeadsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), leadsItemClickListener
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<LeadData>() {
            override fun areItemsTheSame(oldItem: LeadData, newItem: LeadData): Boolean {
                return oldItem.leadId == newItem.leadId
            }

            override fun areContentsTheSame(oldItem: LeadData, newItem: LeadData): Boolean {
                return oldItem == newItem
            }
        }
    }
}

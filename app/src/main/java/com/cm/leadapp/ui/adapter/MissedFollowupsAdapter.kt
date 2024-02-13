package com.cm.leadapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cm.leadapp.R
import com.cm.leadapp.data.responsemodel.FollowupData
import com.cm.leadapp.databinding.ItemFollowupBinding
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.OnFollowupsItemClickListener


class MissedFollowupsAdapter(private val followupsItemClickListener: OnFollowupsItemClickListener) :
    PagingDataAdapter<FollowupData, MissedFollowupsAdapter.FollowupViewHolder>(COMPARATOR) {

    class FollowupViewHolder(
        val binding: ItemFollowupBinding,
        private val itemClickListener: OnFollowupsItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), OnClickListener {
        private val txtClientName = binding.tvClient
        private val imgWhatsapp = binding.ivWhatsapp
        private val imgCall = binding.ivCall
        private val txtProduct = binding.tvProduct
        private val txtStatus = binding.tvStatus
        private val txtTime = binding.tvTime
        private val imgDown = binding.ivDown
        private val imgMarkCompleted = binding.ivMarkCompleted
        private val imgEdit = binding.ivEdit
        private val imgAddFollowup = binding.ivAddFollowup
        private val imgTimeLine = binding.ivTimeline
        private val cardActions = binding.cardActions
        private val imgChangeAgent = binding.ivChangeAgent
        private val imgView = binding.ivView

        private var followupData: FollowupData? = null

        fun bind(item: FollowupData?) {

            item?.let {
                followupData = it
                txtClientName.text = it.client
                it.product?.let { product ->
                    txtProduct.text = product
                }
                txtTime.text = it.followupDate
                GenUtils.setStatus(txtStatus, it.status)

                if (it.isCompleted.equals("Completed")) {

                    imgMarkCompleted.apply {
                        setImageResource(R.drawable.ic_tick_green)
                        isClickable = false
                        tag = "completed"
                    }

                }

            }

            imgCall.setOnClickListener(this)
            imgWhatsapp.setOnClickListener(this)
            imgMarkCompleted.setOnClickListener(this)
            imgAddFollowup.setOnClickListener(this)
            imgEdit.setOnClickListener(this)
            imgTimeLine.setOnClickListener(this)
            imgChangeAgent.setOnClickListener(this)
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
            itemClickListener.onItemClick(followupData, view)

            if(view?.id == R.id.iv_mark_completed){

                ( view as ImageView).apply {
                    setImageResource(R.drawable.ic_tick_green)
                    tag = "completed"
                }
            }

        }

    }

    override fun onBindViewHolder(holder: FollowupViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowupViewHolder {

        return FollowupViewHolder(
            ItemFollowupBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), followupsItemClickListener
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<FollowupData>() {
            override fun areItemsTheSame(oldItem: FollowupData, newItem: FollowupData): Boolean {
                return oldItem.followUpId == newItem.followUpId
            }

            override fun areContentsTheSame(oldItem: FollowupData, newItem: FollowupData): Boolean {
                return oldItem == newItem
            }
        }
    }
}


package com.cm.leadapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cm.kbslead.R
import com.cm.leadapp.data.responsemodel.FinalStatus
import com.cm.leadapp.data.responsemodel.FollowType
import com.cm.leadapp.data.responsemodel.Status
import com.cm.leadapp.data.responsemodel.StatusData
import com.cm.leadapp.util.StatusType

class StatusAdapter(val context: Context, dataSource: StatusData, private val type: StatusType) :
    BaseAdapter() {

    private var list: ArrayList<*>

    init {
        list = when (type) {
            StatusType.STATUS -> dataSource.status
            StatusType.FOLLOWUP -> dataSource.followType
            StatusType.FINAL_STATUS -> dataSource.finalStatus
        }
    }

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_status, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = getItemName(position)
        return view
    }

    private fun getItemName(pos: Int): String? {
        return when (type) {
            StatusType.STATUS -> (list[pos] as Status).name
            StatusType.FOLLOWUP -> (list[pos] as FollowType).name
            StatusType.FINAL_STATUS -> (list[pos] as FinalStatus).name
        }
    }

    override fun getItem(position: Int): Any? {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ItemHolder(row: View?) {

        val label: TextView

        init {
            label = row?.findViewById(R.id.tv_status) as TextView
        }
    }
}

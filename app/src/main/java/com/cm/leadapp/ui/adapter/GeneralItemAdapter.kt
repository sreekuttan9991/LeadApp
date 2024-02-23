package com.cm.leadapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cm.kbslead.R
import com.cm.leadapp.data.responsemodel.CustomerType
import com.cm.leadapp.data.responsemodel.District
import com.cm.leadapp.data.responsemodel.Products
import com.cm.leadapp.data.responsemodel.Source
import com.cm.leadapp.data.responsemodel.State
import com.cm.leadapp.util.GeneralItem

class GeneralItemAdapter(
    val context: Context,
    private var list: ArrayList<*>,
    private val type: GeneralItem
) : BaseAdapter() {


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

            GeneralItem.STATE -> (list[pos] as State).name
            GeneralItem.DISTRICT -> (list[pos] as District).name
            GeneralItem.CUSTOMER -> (list[pos] as CustomerType).name
            GeneralItem.SOURCE -> (list[pos] as Source).name
            GeneralItem.PRODUCT -> (list[pos] as Products).name
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

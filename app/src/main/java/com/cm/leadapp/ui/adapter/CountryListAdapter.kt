package com.cm.leadapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.cm.kbslead.databinding.ItemStatusBinding
import com.cm.leadapp.data.responsemodel.Country
import com.cm.leadapp.util.OnCountryListItemClickListener

class CountryListAdapter(private val onCountryListItemClickListener: OnCountryListItemClickListener) :
    RecyclerView.Adapter<CountryListAdapter.MyViewHolder>(), Filterable {

    private var mList = ArrayList<Country>()
    private var listFiltered = ArrayList<Country>()

    fun setData(countryList: ArrayList<Country>) {
        this.mList = countryList
        this.listFiltered = countryList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ItemStatusBinding.inflate(
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

            tvName.text = data.name

            itemView.setOnClickListener {
                onCountryListItemClickListener.onItemClick(data)
            }
        }
    }

    class MyViewHolder(val binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvName = binding.tvStatus

    }

    override fun getFilter(): Filter {


        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (charSequence.isNullOrEmpty()) {
                    filterResults.count = listFiltered.size
                    filterResults.values = listFiltered
                } else {
                    val searchChar = charSequence.toString().lowercase()
                    val itemModel = ArrayList<Country>()
                    for (item in listFiltered) {
                        if (item.name!!.lowercase().contains(searchChar))
                            itemModel.add(item)
                    }
                    filterResults.count = itemModel.size
                    filterResults.values = itemModel
                }
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {

                mList = results?.values as ArrayList<Country>

                notifyDataSetChanged()

            }

        }

    }
}


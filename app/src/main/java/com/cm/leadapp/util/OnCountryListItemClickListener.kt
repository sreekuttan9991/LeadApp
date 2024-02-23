package com.cm.leadapp.util

import com.cm.leadapp.data.responsemodel.Country

interface OnCountryListItemClickListener {

    fun onItemClick(country: Country)
}
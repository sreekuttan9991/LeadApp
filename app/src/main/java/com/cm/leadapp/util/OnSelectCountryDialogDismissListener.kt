package com.cm.leadapp.util

import com.cm.leadapp.data.responsemodel.Country

interface OnSelectCountryDialogDismissListener {

    fun onDismissed(country: Country)
}
package com.cm.leadapp.util

import android.view.View
import com.cm.leadapp.data.responsemodel.LeadData

interface OnLeadsItemClickListener {

    fun onItemClick(lead : LeadData?, view: View?)
}
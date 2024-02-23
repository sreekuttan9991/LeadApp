package com.cm.leadapp.util

import android.view.View
import com.cm.leadapp.data.responsemodel.ClosedLeadData

interface OnClosedLeadsItemClickListener {

    fun onItemClick(lead: ClosedLeadData?, view: View?)
}
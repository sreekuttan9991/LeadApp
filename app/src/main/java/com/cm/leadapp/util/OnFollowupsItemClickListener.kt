package com.cm.leadapp.util

import android.view.View
import com.cm.leadapp.data.responsemodel.FollowupData

interface OnFollowupsItemClickListener {

    fun onItemClick(followup: FollowupData?, view: View?)
}
package com.cm.leadapp.data.request

data class AddCallLogRequest(
    val data_array: ArrayList<CallLog>,
    val sales_id: String
)
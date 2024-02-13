package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class TrashLeadResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: TrashData? = TrashData(),
    @SerializedName("message") var message: String? = null

)

data class TrashData(
    @SerializedName("contact_id") var id: String? = null,
)

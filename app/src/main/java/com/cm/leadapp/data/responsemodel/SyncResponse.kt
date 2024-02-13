package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class SyncResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: SyncData? = SyncData(),
    @SerializedName("message") var message: String? = null

)

data class SyncData(

    @SerializedName("sales_id") var salesId: Int? = null,
    @SerializedName("last_sync") var lastSync: String? = null

)
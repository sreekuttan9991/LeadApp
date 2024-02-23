package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class MarkCompletedResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: MarkCompletedData? = MarkCompletedData(),
    @SerializedName("message") var message: String? = null

)

data class MarkCompletedData(

    @SerializedName("follow_up_id") var followUpId: Int? = null

)
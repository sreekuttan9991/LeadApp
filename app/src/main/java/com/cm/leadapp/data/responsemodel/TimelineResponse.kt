package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class TimelineResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: ArrayList<TimelineData> = arrayListOf(),
    @SerializedName("message") var message: String? = null

)


data class TimelineData(

    @SerializedName("date") var date: String? = null,
    @SerializedName("group_name") var groupName: String? = null,
    @SerializedName("message") var message: String? = null

)
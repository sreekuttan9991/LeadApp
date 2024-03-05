package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class StreamResponse(
    @SerializedName("data") val data: ArrayList<StreamData> = arrayListOf(),
    @SerializedName("message")val message: String,
    @SerializedName("status") val status: String
)

data class StreamData(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
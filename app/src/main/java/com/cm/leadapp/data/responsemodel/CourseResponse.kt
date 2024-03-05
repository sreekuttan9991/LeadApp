package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("data") val data: ArrayList<CourseData> = arrayListOf(),
    @SerializedName("message")val message: String,
    @SerializedName("status") val status: String
)

data class CourseData(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
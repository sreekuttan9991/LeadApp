package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class DistrictResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: ArrayList<District> = arrayListOf(),
    @SerializedName("message") var message: String? = null

)

data class District(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null

)
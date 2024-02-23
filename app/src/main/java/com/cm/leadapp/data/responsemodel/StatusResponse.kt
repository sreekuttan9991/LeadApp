package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class StatusResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: StatusData? = StatusData(),
    @SerializedName("message") var message: String? = null

)

data class Status(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null

)


data class FinalStatus(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)

data class FollowType(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)

data class StatusData(

    @SerializedName("status") var status: ArrayList<Status> = arrayListOf(),
    @SerializedName("final_status") var finalStatus: ArrayList<FinalStatus> = arrayListOf(),
    @SerializedName("follow_type") var followType: ArrayList<FollowType> = arrayListOf()

)
package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class FollowupResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: ArrayList<FollowupData> = arrayListOf(),
    @SerializedName("message") var message: String? = null

)


data class FollowupData(

    @SerializedName("follow_up_id") var followUpId: String? = null,
    @SerializedName("product") var product: String? = null,
    @SerializedName("lead_id") var leadId: String? = null,
    @SerializedName("client") var client: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("followup_date") var followupDate: String? = null,
    @SerializedName("followup_type") var followupType: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("is_completed") var isCompleted: String? = null

)
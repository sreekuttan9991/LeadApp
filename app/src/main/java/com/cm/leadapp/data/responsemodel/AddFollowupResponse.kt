package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class AddFollowupResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: AddFollowupData? = AddFollowupData(),
    @SerializedName("message") var message: String? = null

)

data class AddFollowupData(

    @SerializedName("contact_id") var contactId: Int? = null,
    @SerializedName("remark") var remark: String? = null,
    @SerializedName("type") var type: Int? = null,
    @SerializedName("followup_date") var followupDate: String? = null,
    @SerializedName("user_id") var userId: Int? = null

)
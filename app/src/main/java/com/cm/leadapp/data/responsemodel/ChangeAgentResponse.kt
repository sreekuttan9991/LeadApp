package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class ChangeAgentResponse (

    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("data"    ) var data    : ChangeAgentData?   = ChangeAgentData(),
    @SerializedName("message" ) var message : String? = null

)

data class ChangeAgentData (

    @SerializedName("user_id" ) var userId : Int? = null

)
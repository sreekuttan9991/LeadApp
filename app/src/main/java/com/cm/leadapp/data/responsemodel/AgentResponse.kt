package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class AgentResponse (

    @SerializedName("status"  ) var status  : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Agent> = arrayListOf(),
    @SerializedName("message" ) var message : String?         = null

)
data class Agent (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)
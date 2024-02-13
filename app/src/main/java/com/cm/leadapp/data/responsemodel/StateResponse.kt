package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class StateResponse (

    @SerializedName("status"  ) var status  : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<State> = arrayListOf(),
    @SerializedName("message" ) var message : String?         = null

)

data class State (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)
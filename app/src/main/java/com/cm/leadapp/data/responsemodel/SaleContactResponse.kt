package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class SaleContactResponse (

    @SerializedName("status"  ) var status  : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<SaleContactData> = arrayListOf(),
    @SerializedName("message" ) var message : String?         = null

)

data class SaleContactData (

    @SerializedName("lead_id"    ) var leadId    : String? = null,
    @SerializedName("user_phone" ) var userPhone : String? = null

)
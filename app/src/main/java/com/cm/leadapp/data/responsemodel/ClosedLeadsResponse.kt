package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class ClosedLeadsResponse (

    @SerializedName("status"  ) var status  : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<ClosedLeadData> = arrayListOf(),
    @SerializedName("message" ) var message : String?         = null

)

data class ClosedLeadData (

    @SerializedName("lead_id"           ) var leadId           : String? = null,
    @SerializedName("product"           ) var product          : String? = null,
    @SerializedName("client"            ) var client           : String? = null,
    @SerializedName("phone"             ) var phone            : String? = null,
    @SerializedName("email"             ) var email            : String? = null,
    @SerializedName("customer_category" ) var customerCategory : String? = null,
    @SerializedName("touch_date"        ) var touchDate        : String? = null,
    @SerializedName("status"            ) var status           : String? = null

)
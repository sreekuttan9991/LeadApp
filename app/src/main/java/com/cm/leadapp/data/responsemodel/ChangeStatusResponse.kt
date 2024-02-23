package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ChangeStatusResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: ChangeStatusData? = ChangeStatusData(),
    @SerializedName("message") var message: String? = null

)

data class ChangeStatusData(

    @SerializedName("status_id") var statusId: Int? = null,
    @SerializedName("final_status") var finalStatus: Int? = null,
    @SerializedName("invoice_no") var invoiceNo: String? = null,
    @SerializedName("models") var models: String? = null,
    @SerializedName("invoice_value") var invoiceValue: String? = null

)
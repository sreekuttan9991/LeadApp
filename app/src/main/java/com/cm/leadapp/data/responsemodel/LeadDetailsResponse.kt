package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class LeadDetailsResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: DetailsData? = DetailsData(),
    @SerializedName("message") var message: String? = null

)

data class ContactDetails(

    @SerializedName("client_name") var clientName: String? = null,
    @SerializedName("client_email") var clientEmail: String? = null,
    @SerializedName("client_phone") var clientPhone: String? = null,
    @SerializedName("source") var source: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("feed_back") var feedBack: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("product") var product: String? = null,
    @SerializedName("cost") var cost: String? = null,
    @SerializedName("brand_name") var brandName: String? = null,
    @SerializedName("invoice_no") var invoiceNo: String? = null,
    @SerializedName("invoice_value") var invoiceValue: String? = null,
    @SerializedName("models") var models: String? = null,
    @SerializedName("reason") var reason: String? = null,
    @SerializedName("final_status") var finalStatus: String? = null

)

data class FollowUpHistory(

    @SerializedName("date") var date: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("remark") var remark: String? = null

)

data class DetailsData(

    @SerializedName("contact_details") var contactDetails: ContactDetails? = ContactDetails(),
    @SerializedName("follow_up_list") var followUpHistory: ArrayList<FollowUpHistory> = arrayListOf()

)
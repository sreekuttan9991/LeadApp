package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class AddNewLeadResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: AddNewLeadData? = AddNewLeadData(),
    @SerializedName("message") var message: String? = null

)

data class AddNewLeadData(

    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("product_id") var productId: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("cost") var cost: String? = null,
    @SerializedName("touch_date") var touchDate: String? = null,
    @SerializedName("source_id") var sourceId: String? = null,
    @SerializedName("type_id") var typeId: String? = null,
    @SerializedName("cgroup_id") var cgroupId: String? = null,
    @SerializedName("city_name") var cityName: String? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("status_id") var statusId: String? = null,
    @SerializedName("created") var created: String? = null

)
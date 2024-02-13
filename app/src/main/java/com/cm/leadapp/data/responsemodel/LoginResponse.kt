package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("data"    ) var data    : UserData?   = UserData(),
    @SerializedName("message" ) var message : String? = null

)

data class UserData (

    @SerializedName("id"        ) var id       : String? = null,
    @SerializedName("name"      ) var name     : String? = null,
    @SerializedName("last_name" ) var lastName : String? = null,
    @SerializedName("email"     ) var email    : String? = null,
    @SerializedName("phone"     ) var phone    : String? = null,
    @SerializedName("group_id"  ) var groupId  : String? = null,
    @SerializedName("otp"       ) var otp      : Int?    = null,
    @SerializedName("response"  ) var response : String? = null

)
package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName


data class ListResponse (

    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("data"    ) var data    : ListData?   = ListData(),
    @SerializedName("message" ) var message : String? = null

)

data class Country (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class CustomerType (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Source (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)


data class Products (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class ListData (

    @SerializedName("country"       ) var countryList      : ArrayList<Country>      = arrayListOf(),
    @SerializedName("customer_type" ) var customerTypeList : ArrayList<CustomerType> = arrayListOf(),
    @SerializedName("source"        ) var sourceList       : ArrayList<Source>       = arrayListOf(),
    @SerializedName("products"      ) var productList     : ArrayList<Products>     = arrayListOf()

)
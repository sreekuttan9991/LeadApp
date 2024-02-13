package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper : ApiHelper) {

    fun getSaleContacts(saleId: String) = apiHelper.getSalesContacts(saleId)

    fun syncCalls(input: JSONObject) = apiHelper.syncCallHistory(input)
}
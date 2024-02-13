package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.ChangeStatusResponse
import com.cm.leadapp.data.responsemodel.StatusResponse
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

class ChangeStatusRepository @Inject constructor(private val apiHelper : ApiHelper) {

    fun getStatusList() : Flow<StatusResponse> = apiHelper.getStatusList()

    fun changeStatus(input: JSONObject) : Flow<ChangeStatusResponse> = apiHelper.changeStatus(input)
}
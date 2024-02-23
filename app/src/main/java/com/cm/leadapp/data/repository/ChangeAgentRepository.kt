package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.AgentResponse
import com.cm.leadapp.data.responsemodel.ChangeAgentResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeAgentRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getAgentList(): Flow<AgentResponse> = apiHelper.getAgentList()

    fun changeAgent(saleId: String, leadId: String): Flow<ChangeAgentResponse> =
        apiHelper.changeAgent(saleId, leadId)
}
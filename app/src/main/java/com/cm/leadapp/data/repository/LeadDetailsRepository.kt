package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.LeadDetailsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LeadDetailsRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getLeadDetails(leadId: String): Flow<LeadDetailsResponse> = apiHelper.getLeadDetails(leadId)
}
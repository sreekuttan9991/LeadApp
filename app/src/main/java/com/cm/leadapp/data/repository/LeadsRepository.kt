package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.TrashLeadResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LeadsRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun trashLead(leadId: String): Flow<TrashLeadResponse> = apiHelper.trashLead(leadId)
}
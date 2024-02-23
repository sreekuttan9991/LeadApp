package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.TimelineResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimelineRepository @Inject constructor(private val apiHelper: ApiHelper) {


    fun getTimeline(leadId: String): Flow<TimelineResponse> = apiHelper.getTimeline(leadId)
}
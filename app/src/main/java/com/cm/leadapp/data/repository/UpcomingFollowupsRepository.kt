package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.MarkCompletedResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpcomingFollowupsRepository @Inject constructor(private val apiHelper : ApiHelper) {

    fun markAsCompleted(followupId: String) : Flow<MarkCompletedResponse> = apiHelper.markAsCompleted(followupId)
}
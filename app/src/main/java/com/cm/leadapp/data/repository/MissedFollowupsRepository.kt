package com.cm.leadapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.paging.MissedFollowupsPageSource
import com.cm.leadapp.data.responsemodel.MarkCompletedResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MissedFollowupsRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getMissedFollowups(saleId: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { MissedFollowupsPageSource(apiHelper, saleId) }
    ).liveData

    fun markAsCompleted(followupId: String): Flow<MarkCompletedResponse> =
        apiHelper.markAsCompleted(followupId)
}
package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.StatisticsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatisticsRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun geStatistics(saleId: String): Flow<StatisticsResponse> = apiHelper.getStatistics(saleId)
}
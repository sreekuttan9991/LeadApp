package com.cm.leadapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.FollowupData
import kotlinx.coroutines.flow.collectLatest


class MissedFollowupsPageSource constructor(private val apiHelper: ApiHelper, val saleId: String ): PagingSource<Int, FollowupData>() {
    override fun getRefreshKey(state: PagingState<Int, FollowupData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowupData> {

        return try {
            val position = params.key ?: 1
            val response = apiHelper.getMissedFollowups(saleId, position)
            var data = ArrayList<FollowupData>()
            response.collectLatest {
               data = it.data
            }
            val nextKey = if(data.size > 0)
                position+1
            else null

            LoadResult.Page(data = data, prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
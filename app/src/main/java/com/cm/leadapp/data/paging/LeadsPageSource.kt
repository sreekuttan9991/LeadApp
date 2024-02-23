package com.cm.leadapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.LeadData
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONObject

class LeadsPageSource(private val apiHelper: ApiHelper, private val input: JSONObject) :
    PagingSource<Int, LeadData>() {
    override fun getRefreshKey(state: PagingState<Int, LeadData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeadData> {
        return try {
            val position = params.key ?: 1
            val response = apiHelper.getLeads(input, position)
            var data = ArrayList<LeadData>()
            response.collectLatest {
                data = it.data
            }
            val nextKey = if (data.size > 0)
                position + 1
            else null

            LoadResult.Page(
                data = data, prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
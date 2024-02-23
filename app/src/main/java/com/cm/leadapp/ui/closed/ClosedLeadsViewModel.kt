package com.cm.leadapp.ui.closed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.paging.ClosedLeadsPageSource
import com.cm.leadapp.data.pref.MySharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ClosedLeadsViewModel @Inject constructor(val apiHelper: ApiHelper, val pref: MySharedPref) :
    ViewModel() {

    private var _currentRequest = MutableLiveData<JSONObject>()

    val data = _currentRequest
        // Limit duplicate Requests (Request class should implement equals())
        .distinctUntilChanged()
        .switchMap { inputs ->
            Pager(
                config = PagingConfig(pageSize = 20, maxSize = 100),
                pagingSourceFactory = { ClosedLeadsPageSource(apiHelper, inputs) }

            ).liveData.cachedIn(viewModelScope)

        }

    fun setCurrentRequest(statusId: String, customerCategory: String, keyWord: String) {
        val input = JSONObject()
        input.put("sales_id", pref.saleId)
        input.put("status_id", statusId)
        input.put("customer_category", customerCategory)
        input.put("keyword", keyWord)
        _currentRequest.postValue(input)
    }
}

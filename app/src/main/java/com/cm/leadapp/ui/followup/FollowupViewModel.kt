package com.cm.leadapp.ui.followup

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
import com.cm.leadapp.data.paging.UpcomingFollowupsPageSource
import com.cm.leadapp.data.responsemodel.MarkCompletedResponse

import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.UpcomingFollowupsRepository
import com.cm.leadapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class FollowupViewModel @Inject constructor(val apiHelper: ApiHelper, val repository: UpcomingFollowupsRepository, val pref: MySharedPref)  : ViewModel() {

    private val _markCompleteResp = MutableLiveData<Event<MarkCompletedResponse>>()
    val markCompleteResp = _markCompleteResp

    fun markAsCompleted(followupId: String) {
        repository.markAsCompleted(followupId).onEach {
            _markCompleteResp.value = Event(it)
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }


    private var _currentRequest = MutableLiveData<JSONObject>()

    val followupData = _currentRequest
        // Limit duplicate Requests (Request class should implement equals())
        .distinctUntilChanged()
        .switchMap { inputs->
            Pager (
                config = PagingConfig(pageSize = 20, maxSize = 100),
                pagingSourceFactory = { UpcomingFollowupsPageSource(apiHelper, inputs) }

            ).liveData.cachedIn(viewModelScope)
        }

    fun setCurrentRequest(type: String){
        val input = JSONObject()
        input.put("sales_id",pref.saleId)
        input.put("type",type)
        _currentRequest.postValue(input)
    }
}
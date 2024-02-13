package com.cm.leadapp.ui.leads

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
import com.cm.leadapp.data.paging.LeadsPageSource
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.LeadsRepository
import com.cm.leadapp.data.responsemodel.TrashLeadResponse
import com.cm.leadapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(val apiHelper: ApiHelper, val repository: LeadsRepository, val pref: MySharedPref) : ViewModel() {

    private var _currentRequest = MutableLiveData<JSONObject>()

    private val _trashLeadResp = MutableLiveData<Event<TrashLeadResponse>>()
    val trashLeadResp = _trashLeadResp

    val data = _currentRequest
        // Limit duplicate Requests (Request class should implement equals())
        .distinctUntilChanged()
        .switchMap { inputs->
            Pager (
                config = PagingConfig(pageSize = 20, maxSize = 100),
                pagingSourceFactory = { LeadsPageSource(apiHelper, inputs) }

            ).liveData.cachedIn(viewModelScope)

        }

    fun setCurrentRequest(statusId: String, customerCategory: String, keyWord: String){
        val input = JSONObject()
        input.put("sales_id",pref.saleId)
        input.put("status_id",statusId)
        input.put("customer_category",customerCategory)
        input.put("keyword",keyWord)
        _currentRequest.postValue(input)
    }

    fun moveLeadToTrash(leadId: String){
        repository.trashLead(leadId).onEach {
            _trashLeadResp.value = Event(it)
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }
}
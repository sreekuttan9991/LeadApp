package com.cm.leadapp.ui.missed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.cm.leadapp.data.responsemodel.MarkCompletedResponse
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.MissedFollowupsRepository
import com.cm.leadapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MissedFollowupViewModel  @Inject constructor(val repository: MissedFollowupsRepository, val pref: MySharedPref) : ViewModel(){

    private val _markCompleteResp = MutableLiveData<Event<MarkCompletedResponse>>()
    val markCompleteResp = _markCompleteResp

    @ExperimentalPagingApi
    fun getMissedFollowups() =
        repository.getMissedFollowups(pref.saleId).cachedIn(viewModelScope)

    fun markAsCompleted(followupId: String) {

        repository.markAsCompleted(followupId).onEach {
            _markCompleteResp.value = Event(it)
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }

}
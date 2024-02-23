package com.cm.leadapp.ui.leaddetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.repository.LeadDetailsRepository
import com.cm.leadapp.data.responsemodel.FollowUpHistory
import com.cm.leadapp.data.uimodel.Info
import com.cm.leadapp.util.Event
import com.cm.leadapp.util.InfoUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PagerViewModel @Inject constructor(private val leadDetailsRepository: LeadDetailsRepository) :
    ViewModel() {

    private val _basicInfoData = MutableLiveData<Event<ArrayList<Info>>>()
    val basicInfoData = _basicInfoData

    private val _followupInfoData = MutableLiveData<Event<ArrayList<Info>>>()
    val followupInfoData = _followupInfoData

    private val _followupHistoryList = MutableLiveData<Event<ArrayList<FollowUpHistory>>>()
    val followupHistoryList = _followupHistoryList

    private val uiMapper = InfoUiMapper()

    fun viewLeadDetails(leadId: String) {
        leadDetailsRepository.getLeadDetails(leadId).onEach {
            _basicInfoData.value = Event(uiMapper.getBasicInfo(it.data!!.contactDetails))
            _followupInfoData.value = Event(uiMapper.getFollowupInfo(it.data!!.contactDetails))
            _followupHistoryList.value = Event(it.data!!.followUpHistory)

        }.catch { println(it) }
            .launchIn(viewModelScope)
    }
}
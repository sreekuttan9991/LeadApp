package com.cm.leadapp.ui.action

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.repository.TimelineRepository
import com.cm.leadapp.data.responsemodel.TimelineData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(private val timelineRepository: TimelineRepository) :
    ViewModel() {

    private val _timelineData = MutableLiveData<ArrayList<TimelineData>>()
    val timelineData = _timelineData

    fun getTimeline(leadId: String) {
        timelineRepository.getTimeline(leadId).onEach {
            _timelineData.value = it.data
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }
}
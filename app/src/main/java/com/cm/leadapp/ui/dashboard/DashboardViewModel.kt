package com.cm.leadapp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.repository.StatisticsRepository
import com.cm.leadapp.data.uimodel.Statistics
import com.cm.leadapp.util.StatisticsUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val statisticsRepository: StatisticsRepository) :
    ViewModel() {

    private val _statsData = MutableLiveData<ArrayList<Statistics>>()
    val statsData = _statsData

    private val mapper = StatisticsUiMapper()

    fun getUserData(saleId: String) {
        statisticsRepository.geStatistics(saleId).onEach {
            _statsData.postValue(mapper.getStatsList(it.statsData))
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }
}
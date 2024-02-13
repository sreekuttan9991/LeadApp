package com.cm.leadapp.ui.action

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.responsemodel.Agent
import com.cm.leadapp.data.responsemodel.ChangeAgentResponse
import com.cm.leadapp.data.repository.ChangeAgentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangeAgentViewModel @Inject constructor(val repository: ChangeAgentRepository) : ViewModel() {



    private val _agentList = MutableLiveData<ArrayList<Agent>>()
    val agentList = _agentList

    private val _changeAgentResponse = MutableLiveData<ChangeAgentResponse>()
    val changeAgentResponse = _changeAgentResponse


    fun getAgentList() {

        repository.getAgentList().onEach {
            _agentList.value = it.data
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun changeAgent(saleId: String, leadId: String){


        repository.changeAgent(saleId,leadId).onEach {
            _changeAgentResponse.value = it
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }

}
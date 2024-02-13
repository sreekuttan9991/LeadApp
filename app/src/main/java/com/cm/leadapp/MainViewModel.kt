package com.cm.leadapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.LeadsRepository
import com.cm.leadapp.data.repository.MainRepository
import com.cm.leadapp.data.request.AddCallLogRequest
import com.cm.leadapp.data.responsemodel.SaleContactResponse
import com.cm.leadapp.data.responsemodel.SyncResponse
import com.cm.leadapp.data.responsemodel.TrashLeadResponse
import com.cm.leadapp.util.Event
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: MainRepository, val pref: MySharedPref) :
    ViewModel() {

    private val _salesContactsResp = MutableLiveData<SaleContactResponse>()
    val salesContactsResp = _salesContactsResp

    private val _syncCallsResp = MutableLiveData<SyncResponse>()
    val syncCallsResp = _syncCallsResp

    fun getSalesContactList() {
        repository.getSaleContacts(pref.saleId).onEach {
            _salesContactsResp.value = it
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun syncCallHistory(request: AddCallLogRequest) {
        val outputJson: String = Gson().toJson(request)
        val jsonObject = JSONObject(outputJson)
        repository.syncCalls(jsonObject).onEach {
            _syncCallsResp.value = it
        }.catch {
            println(it)
        }.launchIn(viewModelScope)
    }
}
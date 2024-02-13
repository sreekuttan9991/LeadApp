package com.cm.leadapp.ui.action

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.responsemodel.AddFollowupResponse
import com.cm.leadapp.data.responsemodel.StatusData
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.AddFollowupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AddFollowupViewModel  @Inject constructor(private val addFollowupRepository: AddFollowupRepository,private val pref: MySharedPref) : ViewModel() {

    private val _validationMessage = MutableLiveData<String>()
val validationMessage = _validationMessage


    private val _statusData = MutableLiveData<StatusData>()
    val statusData = _statusData

    private val _addFollowupResponse = MutableLiveData<AddFollowupResponse>()
    val addFollowupResponse = _addFollowupResponse


    fun getStatusData() {

        addFollowupRepository.getStatusList().onEach {
            _statusData.value = it.data!!
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun validateInputs(date : String, time : String,remark: String){

        if(TextUtils.isEmpty(date))
            _validationMessage.value = "Please pick the date"
        else if(TextUtils.isEmpty(time))
            _validationMessage.value = "Please pick the time"
        else if(TextUtils.isEmpty(remark))
            _validationMessage.value = "Please enter remark"
        else _validationMessage.value = ""

    }

    fun addFollowup(date: String,type: String,leadId: String,remark: String) {

        val input = JSONObject();
        input.put("followup_date",date)
        input.put("lead_id",leadId)
        input.put("type",type)
        input.put("remark",remark)
        input.put("sales_id",pref.saleId)

        addFollowupRepository.addFollowup(input).onEach {
            _addFollowupResponse.value = it
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }



}
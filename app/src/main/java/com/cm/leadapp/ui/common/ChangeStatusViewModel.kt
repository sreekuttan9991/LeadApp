package com.cm.leadapp.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.ChangeStatusRepository
import com.cm.leadapp.data.responsemodel.ChangeStatusResponse
import com.cm.leadapp.data.responsemodel.StatusData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChangeStatusViewModel @Inject constructor(
    val repository: ChangeStatusRepository,
    val pref: MySharedPref
) : ViewModel() {


    private val _statusData = MutableLiveData<StatusData>()
    val statusData = _statusData

    private val _changeStatusResponse = MutableLiveData<ChangeStatusResponse>()
    val changeStatusResponse = _changeStatusResponse


    fun getStatusData() {

        repository.getStatusList().onEach {
            _statusData.value = it.data!!
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun changeStatus(
        leadId: String, statusId: String, finalStatus: String, invoiceNo: String,
        models: String, invoiceValue: String, brandName: String, reason: String
    ) {

        val input = JSONObject()
        input.put("sales_id", pref.saleId)
        input.put("lead_id", leadId)
        input.put("status_id", statusId)
        input.put("final_status", finalStatus)
        input.put("invoice_no", invoiceNo)
        input.put("models", models)
        input.put("invoice_value", invoiceValue)
        input.put("brand_name", brandName)
        input.put("reason", reason)

        repository.changeStatus(input).onEach {
            _changeStatusResponse.value = it
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }

}
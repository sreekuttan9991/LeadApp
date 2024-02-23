package com.cm.leadapp.ui.addlead

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.repository.AddNewLeadRepository
import com.cm.leadapp.data.responsemodel.AddNewLeadResponse
import com.cm.leadapp.data.responsemodel.Country
import com.cm.leadapp.data.responsemodel.CustomerType
import com.cm.leadapp.data.responsemodel.District
import com.cm.leadapp.data.responsemodel.Products
import com.cm.leadapp.data.responsemodel.Source
import com.cm.leadapp.data.responsemodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AddNewLeadViewModel @Inject constructor(
    val repository: AddNewLeadRepository,
    val pref: MySharedPref
) : ViewModel() {

    private val _customerName = MutableStateFlow("")
    private val _customerPhone = MutableStateFlow("")
    private val _city = MutableStateFlow("")

    private val _countryList = MutableLiveData<ArrayList<Country>>()
    val countryList = _countryList

    private val _customerTypeList = MutableLiveData<ArrayList<CustomerType>>()
    val customerTypeList = _customerTypeList

    private val _sourceList = MutableLiveData<ArrayList<Source>>()
    val sourceList = _sourceList

    private val _productList = MutableLiveData<ArrayList<Products>>()
    val productList = _productList

    private val _stateList = MutableLiveData<ArrayList<State>>()
    val stateList = _stateList

    private val _districtList = MutableLiveData<ArrayList<District>>()
    val districtList = _districtList

    private val _addNewLeadResponse = MutableLiveData<AddNewLeadResponse>()
    val addNewLeadResponse = _addNewLeadResponse

    fun getGeneralList() {
        repository.getGeneralList().onEach {
            it.data.apply {
                _countryList.value = this?.countryList
                _customerTypeList.value = this?.customerTypeList
                _sourceList.value = this?.sourceList
                _productList.value = this?.productList
            }
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun getStates(countryId: String) {
        repository.getStateList(countryId).onEach {
            _stateList.value = it.data
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun getDistricts(stateId: String) {
        repository.getDistrictList(stateId).onEach {
            _districtList.value = it.data
        }.catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun addNewLead(
        name: String, phone: String, phone1: String, email: String, followupDate: String,
        productId: String, cost: String, touchDate: String, sourceId: String,
        feedbackId: String, customerCategoryId: String, countryId: String,
        stateId: String, districtId: String, city: String
    ) {
        val input = JSONObject()
        input.put("sales_id", pref.saleId)
        input.put("name", name)
        input.put("phone", phone)
        input.put("user_phone1", phone1)
        input.put("email", email)
        input.put("followup_date", followupDate)
        input.put("product_id", productId)
        input.put("cost", cost)
        input.put("touch_date", touchDate)
        input.put("source_id", sourceId)
        input.put("feed_back", feedbackId)
        input.put("customer_category", customerCategoryId)
        input.put("country_id", countryId)
        input.put("state_id", stateId)
        input.put("city_id", districtId)
        input.put("city", city)
        repository.addNewLead(input).onEach {
            _addNewLeadResponse.value = it

        }.catch { println(it) }
            .launchIn(viewModelScope)
    }

    fun setCustomerName(name: String) {
        _customerName.value = name
    }

    fun setCustomerPhone(phone: String) {
        _customerPhone.value = phone
    }

    fun setCity(city: String) {
        _city.value = city
    }

    val isSubmitEnabled: Flow<Boolean> =
        combine(_customerName, _customerPhone, _city) { name, phone, city ->
            val isNameValid = name.length > 2
            val isPhoneValid = phone.length >= 10
            val isCityValid = city.length > 2
            return@combine isNameValid and isPhoneValid and isCityValid
        }
}
package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.AddNewLeadResponse
import com.cm.leadapp.data.responsemodel.DistrictResponse
import com.cm.leadapp.data.responsemodel.ListResponse
import com.cm.leadapp.data.responsemodel.StateResponse
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

class AddNewLeadRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getGeneralList(): Flow<ListResponse> = apiHelper.getGeneralList()

    fun getStateList(countryId: String): Flow<StateResponse> = apiHelper.getStateList(countryId)

    fun getDistrictList(stateId: String): Flow<DistrictResponse> =
        apiHelper.getDistrictList(stateId)

    fun addNewLead(input: JSONObject): Flow<AddNewLeadResponse> = apiHelper.addNewLead(input)
}
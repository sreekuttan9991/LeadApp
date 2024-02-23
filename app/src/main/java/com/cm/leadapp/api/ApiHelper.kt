package com.cm.leadapp.api

import com.cm.leadapp.data.responsemodel.AddFollowupResponse
import com.cm.leadapp.data.responsemodel.AddNewLeadResponse
import com.cm.leadapp.data.responsemodel.AgentResponse
import com.cm.leadapp.data.responsemodel.ChangeAgentResponse
import com.cm.leadapp.data.responsemodel.ChangeStatusResponse
import com.cm.leadapp.data.responsemodel.ClosedLeadsResponse
import com.cm.leadapp.data.responsemodel.DistrictResponse
import com.cm.leadapp.data.responsemodel.FollowupResponse
import com.cm.leadapp.data.responsemodel.LeadDetailsResponse
import com.cm.leadapp.data.responsemodel.LeadResponse
import com.cm.leadapp.data.responsemodel.ListResponse
import com.cm.leadapp.data.responsemodel.LoginResponse
import com.cm.leadapp.data.responsemodel.MarkCompletedResponse
import com.cm.leadapp.data.responsemodel.SaleContactResponse
import com.cm.leadapp.data.responsemodel.StateResponse
import com.cm.leadapp.data.responsemodel.StatisticsResponse
import com.cm.leadapp.data.responsemodel.StatusResponse
import com.cm.leadapp.data.responsemodel.SyncResponse
import com.cm.leadapp.data.responsemodel.TimelineResponse
import com.cm.leadapp.data.responsemodel.TrashLeadResponse
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface ApiHelper {

    fun getUser(email: String): Flow<LoginResponse>

    fun getStatistics(saleId: String): Flow<StatisticsResponse>

    fun getUpcomingFollowups(input: JSONObject, page: Int): Flow<FollowupResponse>

    fun getMissedFollowups(saleId: String, page: Int): Flow<FollowupResponse>

    fun getLeads(input: JSONObject, page: Int): Flow<LeadResponse>

    fun markAsCompleted(followupId: String): Flow<MarkCompletedResponse>

    fun getStatusList(): Flow<StatusResponse>

    fun addFollowups(inputs: JSONObject): Flow<AddFollowupResponse>

    fun changeStatus(inputs: JSONObject): Flow<ChangeStatusResponse>

    fun getTimeline(leadId: String): Flow<TimelineResponse>

    fun getAgentList(): Flow<AgentResponse>

    fun changeAgent(saleId: String, leadId: String): Flow<ChangeAgentResponse>

    fun getLeadDetails(leadId: String): Flow<LeadDetailsResponse>

    fun getGeneralList(): Flow<ListResponse>

    fun getStateList(countryId: String): Flow<StateResponse>

    fun getDistrictList(stateId: String): Flow<DistrictResponse>

    fun addNewLead(inputs: JSONObject): Flow<AddNewLeadResponse>

    fun getClosedLeads(input: JSONObject, page: Int): Flow<ClosedLeadsResponse>

    fun trashLead(leadId: String): Flow<TrashLeadResponse>

    fun getSalesContacts(saleId: String): Flow<SaleContactResponse>

    fun syncCallHistory(inputs: JSONObject): Flow<SyncResponse>
}
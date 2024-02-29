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
import com.cm.leadapp.util.InputUtil
import com.cm.leadapp.util.InputUtil.Companion.getFollowupsInput
import com.cm.leadapp.util.InputUtil.Companion.getInputFromLeadId
import com.cm.leadapp.util.InputUtil.Companion.getInputFromSaleIdAndLeadId
import com.cm.leadapp.util.InputUtil.Companion.getLeadsInput
import com.cm.leadapp.util.InputUtil.Companion.getLoginInputs
import com.cm.leadapp.util.InputUtil.Companion.getMarkCompletedInput
import com.cm.leadapp.util.InputUtil.Companion.getRequestBodyFromJsonObject
import com.cm.leadapp.util.InputUtil.Companion.getStatisticsInputs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val service: LeadApiInterface) : ApiHelper {

    override fun getUser(email: String): Flow<LoginResponse> =
        flow { emit(service.postLogin(getLoginInputs(email))) }

    override fun getStatistics(saleId: String): Flow<StatisticsResponse> =
        flow {
            val result = withContext(Dispatchers.IO) {
                service.getStatistics(
                    getStatisticsInputs(saleId)
                )
            }
            emit(result)
        }

    override fun getUpcomingFollowups(input: JSONObject, page: Int): Flow<FollowupResponse> {
        return flow { emit(service.getUpcomingFollowups(getLeadsInput(input, page))) }
    }

    override fun getMissedFollowups(saleId: String, page: Int): Flow<FollowupResponse> {
        return flow {
            emit(service.getMissedFollowups(getFollowupsInput(saleId, page)))
        }
    }

    override fun getLeads(input: JSONObject, page: Int): Flow<LeadResponse> {
        return flow {
            emit(service.getLeads(getLeadsInput(input, page)))
        }
    }

    override fun markAsCompleted(followupId: String): Flow<MarkCompletedResponse> {
        return flow {
            emit(service.markAsCompleted(getMarkCompletedInput(followupId)))
        }
    }

    override fun getStatusList(): Flow<StatusResponse> {
        return flow {
            emit(service.getStatusList())
        }
    }

    override fun addFollowups(inputs: JSONObject): Flow<AddFollowupResponse> {
        return flow { emit(service.addFollowup(getRequestBodyFromJsonObject(inputs))) }
    }

    override fun changeStatus(inputs: JSONObject): Flow<ChangeStatusResponse> {
        return flow { emit(service.changeStatus(getRequestBodyFromJsonObject(inputs))) }
    }

    override fun getTimeline(leadId: String): Flow<TimelineResponse> {
        return flow {
            emit(service.getTimeline(getInputFromLeadId(leadId)))
        }
    }

    override fun getAgentList(): Flow<AgentResponse> {
        return flow {
            emit(service.getAgentList())
        }
    }

    override fun changeAgent(saleId: String, leadId: String): Flow<ChangeAgentResponse> {
        return flow {
            emit(service.changeAgent(getInputFromSaleIdAndLeadId(saleId, leadId)))
        }
    }

    override fun getLeadDetails(leadId: String): Flow<LeadDetailsResponse> {
        return flow {
            emit(service.getLeadDetails(getInputFromLeadId(leadId)))
        }
    }

    override fun getGeneralList(): Flow<ListResponse> {
        return flow {
            emit(service.getGeneralList())
        }
    }

    override fun getStateList(countryId: String): Flow<StateResponse> {
        return flow {
            emit(service.getStateList(InputUtil.getStatesInput(countryId)))
        }
    }

    override fun getDistrictList(stateId: String): Flow<DistrictResponse> {
        return flow {
            emit(service.getDistrictList(InputUtil.getDistrictsInput(stateId)))
        }
    }

    override fun addNewLead(inputs: JSONObject): Flow<AddNewLeadResponse> {
        return flow {
            emit(service.addNewLead(getRequestBodyFromJsonObject(inputs)))
        }
    }

    override fun getClosedLeads(input: JSONObject, page: Int): Flow<ClosedLeadsResponse> {
        return flow {
            emit(service.getClosedLeads(getLeadsInput(input, page)))
        }
    }

    override fun trashLead(leadId: String): Flow<TrashLeadResponse> {
        return flow {
            emit(service.trashLead(getInputFromLeadId(leadId)))
        }
    }

    override fun getSalesContacts(saleId: String): Flow<SaleContactResponse> {
        return flow {
            emit(service.getSaleContacts(getStatisticsInputs(saleId)))
        }
    }

    override fun syncCallHistory(inputs: JSONObject): Flow<SyncResponse> {
        return flow {
            val result = withContext(Dispatchers.IO) {
                service.syncCallHistory(getRequestBodyFromJsonObject(inputs))
            }
            emit(result)
        }
    }
}
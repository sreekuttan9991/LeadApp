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
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface LeadApiInterface {

    companion object {
        const val BASE_URL = "https:/demo2.coreleads.in/token/user_api/"

        // PROD: https://crm.kbs.edu.in/
        // STAGING : https://core.coreleads.in/
        // TEST : https://demo2.coreleads.in/
    }

    @Headers("Content-Type: application/json")
    @POST("get_otp_by_email")
    suspend fun postLogin(@Body body: RequestBody): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("get_statistics")
    suspend fun getStatistics(@Body body: RequestBody): StatisticsResponse

    @Headers("Content-Type: application/json")
    @POST("get_upcoming_followups")
    suspend fun getUpcomingFollowups(@Body body: RequestBody): FollowupResponse

    @Headers("Content-Type: application/json")
    @POST("missed_followups")
    suspend fun getMissedFollowups(@Body body: RequestBody): FollowupResponse

    @Headers("Content-Type: application/json")
    @POST("mark_completed")
    suspend fun markAsCompleted(@Body body: RequestBody): MarkCompletedResponse

    @Headers("Content-Type: application/json")
    @POST("leads")
    suspend fun getLeads(@Body body: RequestBody): LeadResponse

    @Headers("Content-Type: application/json")
    @GET("get_status_list")
    suspend fun getStatusList(): StatusResponse

    @Headers("Content-Type: application/json")
    @POST("add_followup")
    suspend fun addFollowup(@Body body: RequestBody): AddFollowupResponse

    @Headers("Content-Type: application/json")
    @POST("change_status")
    suspend fun changeStatus(@Body body: RequestBody): ChangeStatusResponse

    @Headers("Content-Type: application/json")
    @POST("get_time_line")
    suspend fun getTimeline(@Body body: RequestBody): TimelineResponse

    @Headers("Content-Type: application/json")
    @GET("get_agent_list")
    suspend fun getAgentList(): AgentResponse

    @Headers("Content-Type: application/json")
    @POST("change_agent")
    suspend fun changeAgent(@Body body: RequestBody): ChangeAgentResponse

    @Headers("Content-Type: application/json")
    @POST("view_lead_details")
    suspend fun getLeadDetails(@Body body: RequestBody): LeadDetailsResponse

    @Headers("Content-Type: application/json")
    @GET("get_array_list")
    suspend fun getGeneralList(): ListResponse

    @Headers("Content-Type: application/json")
    @POST("get_state")
    suspend fun getStateList(@Body body: RequestBody): StateResponse

    @Headers("Content-Type: application/json")
    @POST("get_district")
    suspend fun getDistrictList(@Body body: RequestBody): DistrictResponse

    @Headers("Content-Type: application/json")
    @POST("add_lead")
    suspend fun addNewLead(@Body body: RequestBody): AddNewLeadResponse

    @Headers("Content-Type: application/json")
    @POST("closed_leads")
    suspend fun getClosedLeads(@Body body: RequestBody): ClosedLeadsResponse

    @Headers("Content-Type: application/json")
    @POST("trash_lead")
    suspend fun trashLead(@Body body: RequestBody): TrashLeadResponse

    @Headers("Content-Type: application/json")
    @POST("get_sales_contact")
    suspend fun getSaleContacts(@Body body: RequestBody): SaleContactResponse

    @Headers("Content-Type: application/json")
    @POST("add_call_duration")
    suspend fun syncCallHistory(@Body body: RequestBody): SyncResponse
}

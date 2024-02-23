package com.cm.leadapp.data.responsemodel

import com.google.gson.annotations.SerializedName

data class StatisticsResponse(

    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var statsData: StatsData? = StatsData(),
    @SerializedName("message") var message: String? = null

)

data class StatsData(

    @SerializedName("total_leads") var totalLeads: Int? = null,
    @SerializedName("leads_closed_today") var leadsClosedToday: Int? = null,
    @SerializedName("leads_closed_value_today") var leadsClosedValueToday: Int? = null,
    @SerializedName("followups_today") var followupsToday: Int? = null,
    @SerializedName("total_untouch") var untouchedLeads: Int? = null,
    @SerializedName("total_closed_value") var totalClosedValue: Int? = null,
    @SerializedName("total_leads_created_today") var totalLeadsCreatedToday: Int? = null,
    @SerializedName("target") var target: String? = null,
    @SerializedName("target_achieved") var targetAchieved: Int? = null,
    @SerializedName("target_pending") var targetPending: Int? = null

)
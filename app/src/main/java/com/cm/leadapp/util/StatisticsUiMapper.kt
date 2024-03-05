package com.cm.leadapp.util

import com.cm.leadapp.data.responsemodel.StatsData
import com.cm.leadapp.data.uimodel.Statistics

class StatisticsUiMapper {

    fun getStatsList(statsData: StatsData?): ArrayList<Statistics> {
        val statList = ArrayList<Statistics>()
        statList.add(Statistics("Total Leads", "" + statsData?.totalLeads))
        statList.add(Statistics("Total Leads Today", "" + statsData?.totalLeadsCreatedToday))
        statList.add(Statistics("Followups Today", "" + statsData?.followupsToday))
        statList.add(Statistics("Leads Closed Today", "" + statsData?.leadsClosedToday))
        return statList
    }
}
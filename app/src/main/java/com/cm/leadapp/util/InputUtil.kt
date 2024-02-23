package com.cm.leadapp.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class InputUtil {

    companion object {

        fun getLoginInputs(email: String): RequestBody {
            val input = JSONObject()
            input.put("email", email)
            return getRequestBodyFromJsonObject(input)
        }

        fun getStatisticsInputs(saleId: String): RequestBody {
            val input = JSONObject()
            input.put("sales_id", saleId)
            return getRequestBodyFromJsonObject(input)
        }

        fun getFollowupsInput(saleId: String, pageNo: Int): RequestBody {
            val input = JSONObject()
            input.put("sales_id", saleId)
            input.put("page_no", pageNo)
            return getRequestBodyFromJsonObject(input)
        }

        fun getLeadsInput(input: JSONObject, pageNo: Int): RequestBody {
            input.put("page_no", pageNo)
            return getRequestBodyFromJsonObject(input)
        }

        fun getMarkCompletedInput(followupId: String): RequestBody {
            val input = JSONObject()
            input.put("followup_id", followupId)
            return getRequestBodyFromJsonObject(input)
        }

        fun getInputFromLeadId(leadId: String): RequestBody {
            val input = JSONObject()
            input.put("lead_id", leadId)
            return getRequestBodyFromJsonObject(input)
        }

        fun getInputFromSaleIdAndLeadId(saleId: String, leadId: String): RequestBody {
            val input = JSONObject()
            input.put("sales_id", saleId)
            input.put("lead_id", leadId)
            return getRequestBodyFromJsonObject(input)
        }

        fun getStatesInput(countryId: String): RequestBody {
            val input = JSONObject()
            input.put("country_id", countryId)
            return getRequestBodyFromJsonObject(input)
        }

        fun getDistrictsInput(stateId: String): RequestBody {
            val input = JSONObject()
            input.put("state_id", stateId)
            return getRequestBodyFromJsonObject(input)
        }

        fun getRequestBodyFromJsonObject(input: JSONObject): RequestBody {
            return (input.toString()).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        }
    }
}
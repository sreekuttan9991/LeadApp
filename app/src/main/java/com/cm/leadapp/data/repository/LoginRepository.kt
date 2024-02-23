package com.cm.leadapp.data.repository

import com.cm.leadapp.api.ApiHelper
import com.cm.leadapp.data.responsemodel.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getUser(email: String): Flow<LoginResponse> = apiHelper.getUser(email)

}
package com.cm.leadapp.ui.onboarding


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cm.leadapp.data.repository.LoginRepository
import com.cm.leadapp.data.responsemodel.LoginResponse
import com.cm.leadapp.data.responsemodel.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val repository: LoginRepository) :

    ViewModel() {

    private val _userData = MutableLiveData<UserData?>()

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse = _loginResponse

    val userOtp = MutableLiveData<Int?>()
    val saleId = MutableLiveData<String?>()
    val userName = MutableLiveData<String?>()
    val userEmail = MutableLiveData<String?>()

    fun getUserData(email: String) {
        repository.getUser(email).onEach {
            _loginResponse.value = it
            _userData.value = it.data.also { userData ->
                userOtp.value = userData?.otp
                saleId.value = userData?.id
                userName.value = userData?.name
                userEmail.value = userData?.email
            }
        }
            .catch { println(it) }
            .launchIn(viewModelScope)
    }
}
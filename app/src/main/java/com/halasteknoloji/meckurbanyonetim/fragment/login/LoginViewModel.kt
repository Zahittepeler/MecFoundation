package com.halasteknoloji.meckurbanyonetim.fragment.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.halasteknoloji.meckurbanyonetim.api.Repository
import com.halasteknoloji.meckurbanyonetim.models.GlobalResponse
import com.halasteknoloji.meckurbanyonetim.models.LoginUserRequestBody
import kotlinx.coroutines.Dispatchers

class LoginViewModel : ViewModel() {

    fun loginUser(UserName: String?, Password: String?) =

        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            try {
                val loginUserRequestBody = LoginUserRequestBody(UserName, Password)

                emit(GlobalResponse.success(data = Repository.loginUser(loginUserRequestBody = loginUserRequestBody)))
            } catch (e: java.lang.Exception) {
                Log.d("Error", e.message.toString())
                emit(
                    GlobalResponse.error<LoginUserRequestBody>(
                        message = "Error"
                    )
                )
            }
        }
}
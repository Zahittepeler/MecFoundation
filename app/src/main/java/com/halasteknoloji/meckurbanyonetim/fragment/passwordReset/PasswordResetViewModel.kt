package com.halasteknoloji.meckurbanyonetim.fragment.passwordReset

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.halasteknoloji.meckurbanyonetim.api.Repository
import com.halasteknoloji.meckurbanyonetim.models.GlobalResponse
import com.halasteknoloji.meckurbanyonetim.models.PasswordResetRequestBody
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PasswordResetViewModel: ViewModel() {

    fun resetPassword(GonulluID: String?, NewPassword: String?, OldPassword: String?, ChangePasswordState: Boolean?) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            try {
                val passwordResetRequestBody = PasswordResetRequestBody(GonulluID = GonulluID, NewPassword = NewPassword, OldPassword = OldPassword, ChangePasswordState = ChangePasswordState)

                emit(GlobalResponse.success(data = Repository.getChangePassword(passwordResetRequestBody = passwordResetRequestBody)))
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                emit(
                    GlobalResponse.error<PasswordResetRequestBody>(
                        message = "Error"
                    )
                )
            }
        }

}
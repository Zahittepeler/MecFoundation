package com.halasteknoloji.meckurbanyonetim.api

import com.halasteknoloji.meckurbanyonetim.models.AddDistributionRequestBody
import com.halasteknoloji.meckurbanyonetim.models.LoginUserRequestBody
import com.halasteknoloji.meckurbanyonetim.models.PasswordResetRequestBody
import retrofit2.http.Body

object Repository {
    private val apiService = ApiModule.createApiService()

    suspend fun loginUser(loginUserRequestBody: LoginUserRequestBody) = apiService?.loginUser(loginUserRequestBody = loginUserRequestBody)

    suspend fun addDistribution(addDistributionRequestBody: AddDistributionRequestBody) = apiService?.addDistribution(addDistributionRequestBody = addDistributionRequestBody)

    suspend fun getDistributionList(myListRequestBody: AddDistributionRequestBody) = apiService?.getDistributionList(addDistributionRequestBody = myListRequestBody)

    suspend fun getChangePassword(@Body passwordResetRequestBody: PasswordResetRequestBody) = apiService?.getChangePassword(passwordResetRequestBody = passwordResetRequestBody)

    suspend fun getRemoveDistribution(@Body addDistributionRequestBody: AddDistributionRequestBody) = apiService?.getRemoveDistribution(addDistributionRequestBody = addDistributionRequestBody)
}
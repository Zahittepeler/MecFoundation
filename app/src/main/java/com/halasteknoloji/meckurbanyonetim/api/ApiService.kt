package com.halasteknoloji.meckurbanyonetim.api

import AddDistributionResponseBody
import com.halasteknoloji.meckurbanyonetim.models.*
import retrofit2.http.*

interface ApiService {

    @POST("Controller/DagitimController.ashx?action=LoginUser")
    suspend fun loginUser(@Body loginUserRequestBody: LoginUserRequestBody): LoginUserResponseBody


    @POST("Controller/DagitimController.ashx?action=AddDagitim")
    suspend fun addDistribution(@Body addDistributionRequestBody: AddDistributionRequestBody): AddDistributionResponseBody

    @POST("Controller/DagitimController.ashx?action=MyList")
    suspend fun getDistributionList(@Body addDistributionRequestBody: AddDistributionRequestBody): List<Volunteer>?

    @POST("Controller/DagitimController.ashx?action=ChangePassword")
    suspend fun getChangePassword(@Body passwordResetRequestBody: PasswordResetRequestBody): PasswordResetResponseBody

    @POST("Controller/DagitimController.ashx?action=RemoveDagitim")
    suspend fun getRemoveDistribution(@Body addDistributionRequestBody: AddDistributionRequestBody): PasswordResetResponseBody

}
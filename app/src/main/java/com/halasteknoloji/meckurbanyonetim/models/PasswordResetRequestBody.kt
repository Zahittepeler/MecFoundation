package com.halasteknoloji.meckurbanyonetim.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PasswordResetRequestBody(
    @field:Json(name = "GonulluID")
    var GonulluID: String? = null,
    @field:Json(name = "NewPassword")
    var NewPassword: String? = null,
    @field:Json(name = "OldPassword")
    var OldPassword: String? = null,
    @field:Json(name = "ChangePasswordState")
    var ChangePasswordState: Boolean? = null,
): Parcelable

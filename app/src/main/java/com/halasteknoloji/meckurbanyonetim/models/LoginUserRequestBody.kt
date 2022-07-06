package com.halasteknoloji.meckurbanyonetim.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUserRequestBody(
    @field:Json(name = "UserName")
    var UserName: String? = null,
    @field:Json(name = "Password")
    var Password: String? = null
): Parcelable
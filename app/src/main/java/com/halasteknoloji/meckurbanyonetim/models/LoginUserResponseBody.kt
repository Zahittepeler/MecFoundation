package com.halasteknoloji.meckurbanyonetim.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginUserResponseBody(
    @field:Json(name = "Id")
    var Id: String? = null,
    @field:Json(name = "UserName")
    var UserName: String? = null,
    @field:Json(name = "FullName")
    var FullName: String? = null,
    @field:Json(name = "City")
    var City: String? = null,
    @field:Json(name = "State")
    var State: Boolean? = null,
    @field:Json(name = "ManagementUser")
    var ManagementUser: String? = null,
    @field:Json(name = "Password")
    var Password: String? = null,
    @field:Json(name = "ChangePassword")
    var ChangePassword: Boolean? = null,
): Parcelable
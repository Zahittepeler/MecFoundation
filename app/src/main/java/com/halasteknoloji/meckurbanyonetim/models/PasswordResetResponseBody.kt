package com.halasteknoloji.meckurbanyonetim.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PasswordResetResponseBody(
    @field:Json(name = "Id")
    var Id: String? = null,
    @field:Json(name = "Message")
    var Message: String? = null,
    @field:Json(name = "State")
    var State: Boolean? = null,
): Parcelable
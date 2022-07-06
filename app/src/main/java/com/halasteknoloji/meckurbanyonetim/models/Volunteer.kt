package com.halasteknoloji.meckurbanyonetim.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Volunteer(
    @field:Json(name = "ID")
    var ID: String? = null,
    @field:Json(name = "TCID")
    var TCID: String? = null,
    @field:Json(name = "Name")
    var Name: String? = null,
    @field:Json(name = "SurName")
    var SurName: String? = null,
    @field:Json(name = "City")
    var City: String? = null,
    @field:Json(name = "Coordinate")
    var Coordinate: String? = null,
    @field:Json(name = "PackageCount")
    var PackageCount: String? = null,
    @field:Json(name = "State")
    var State: Boolean? = null,
    @field:Json(name = "GonulluID")
    var GonulluID: String? = null,
): Parcelable

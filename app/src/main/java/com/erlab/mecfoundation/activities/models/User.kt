package com.erlab.mecfoundation.activities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var eMail:String? = null,
    var password:String? = null,
    var name:String? = null
):Parcelable

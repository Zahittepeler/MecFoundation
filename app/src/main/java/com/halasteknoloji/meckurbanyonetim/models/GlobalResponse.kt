package com.halasteknoloji.meckurbanyonetim.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GlobalResponse<T : Parcelable>(
    var status: ResponseStatus,
    var data: T?,
    var list: List<Volunteer>? = null,
    var message: String?
) : Parcelable {

    companion object {
        fun <T : Parcelable> success(data: T?) =
            GlobalResponse(status = ResponseStatus.SUCCESS, data = data, message = null)

        fun listSuccess(list: List<Volunteer>?) =
            GlobalResponse(status = ResponseStatus.SUCCESS, data = null, list = list, message = null)

        fun <T : Parcelable> error(message: String?) =
            GlobalResponse(status = ResponseStatus.ERROR, data = null, message = message)
    }

}

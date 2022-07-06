import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddDistributionResponseBody(
    @field:Json(name = "State")
    var State: Boolean? = null,
    @field:Json(name = "Message")
    var Message: String? = null,
    @field:Json(name = "Id")
    var Id: String? = null,
) : Parcelable
package com.halasteknoloji.meckurbanyonetim.fragment.purchase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.halasteknoloji.meckurbanyonetim.api.Repository
import com.halasteknoloji.meckurbanyonetim.models.AddDistributionRequestBody
import com.halasteknoloji.meckurbanyonetim.models.GlobalResponse
import com.halasteknoloji.meckurbanyonetim.models.LoginUserRequestBody
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PurchaseViewModel : ViewModel() {

    fun distribution(TCID: String?, Name: String?, SurName: String?, Coordinate: String?, PackageCount: String?, Volunteer: String?, City: String?, ImzaBinary : String?) =

        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            try {
                val addDistributionRequestBody = AddDistributionRequestBody(TCID = TCID, Name = Name, SurName = SurName, Coordinate = "40.0082,28.0784", PackageCount = PackageCount, GonulluID = Volunteer, City = City, ImzaBinary = ImzaBinary)

                emit(GlobalResponse.success(data = Repository.addDistribution(addDistributionRequestBody = addDistributionRequestBody)))
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                emit(
                    GlobalResponse.error<LoginUserRequestBody>(
                        message = "Error"
                    )
                )
            }
        }
}
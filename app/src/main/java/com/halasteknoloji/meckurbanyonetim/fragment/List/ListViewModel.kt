package com.halasteknoloji.meckurbanyonetim.fragment.List

import android.util.Log
import androidx.lifecycle.*
import com.halasteknoloji.meckurbanyonetim.api.Repository
import com.halasteknoloji.meckurbanyonetim.models.*
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ListViewModel : ViewModel() {

    private val _listData: MutableLiveData<MutableList<Volunteer>> = MutableLiveData()

    val listData: MutableLiveData<MutableList<Volunteer>>
        get() = _listData


    fun addData(data: MutableList<Volunteer>) {
        listData.value = data
    }

    fun removeData(ID: String) {

      /*  _listData.value?.removeIf{
            it.TCID == TCID
        }
   */

        _listData.value?.forEach {
            if (it.ID == ID) {
                it.State = false
            }
        }

    }

    fun getList(VolunteerId: String?) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            try {
                val addDistributionRequestBody = AddDistributionRequestBody(GonulluID = VolunteerId)

                emit(
                    GlobalResponse.listSuccess(
                        list = Repository.getDistributionList(
                            myListRequestBody = addDistributionRequestBody
                        )
                    )
                )
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                emit(
                    GlobalResponse.error<LoginUserRequestBody>(
                        message = "Error"
                    )
                )
            }
        }

    fun removeDistribution(ID: String?, VolunteerId: String?) =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            try {
                val addDistributionRequestBody =
                    AddDistributionRequestBody(ID = ID, GonulluID = VolunteerId)

                emit(
                    GlobalResponse.success(
                        data = Repository.getRemoveDistribution(
                            addDistributionRequestBody = addDistributionRequestBody
                        )
                    )
                )
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
                emit(
                    GlobalResponse.error<PasswordResetResponseBody>(
                        message = "Error"
                    )
                )
            }
        }
}
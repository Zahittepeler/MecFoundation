package com.halasteknoloji.meckurbanyonetim.fragment.purchase

import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.halasteknoloji.meckurbanyonetim.activities.getBase64Bitmap
import com.halasteknoloji.meckurbanyonetim.fragment.components.ConfirmDialogFragment
import com.halasteknoloji.meckurbanyonetim.activities.hasPermission
import com.halasteknoloji.meckurbanyonetim.models.ResponseStatus
import com.halasteknoloji.meckurbanyonetim.databinding.FragmentPurchaseBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.halasteknoloji.meckurbanyonetim.activities.showSoftKeyboard
import java.util.*


class PurchaseFragment : Fragment() {

    var num = 1


    private var binding: FragmentPurchaseBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val purchaseVİewModel: PurchaseViewModel by viewModels()
    var userId: String? = null
    var volunteerID: String? = null
    var volunteerFullName: String? = null
    var city: String? = null
    private var signBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        userId = arguments?.getString("userId")
        volunteerID = arguments?.getString("volunteerID")
        volunteerFullName = arguments?.getString("volunteerFullName")
        city = arguments?.getString("city")

        binding?.edtLoginName?.requestFocus()
        binding?.edtLoginName?.let { showSoftKeyboard(it, requireContext()) }

        binding?.txtFragmentPurchaseTcId?.text = userId

        binding?.imgPurchaseBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding?.btnPurchaseBuy?.setOnClickListener {
            ConfirmDialogFragment(volunteerID = volunteerID, count = binding?.variable?.text.toString(), volunteerFullName = volunteerFullName.toString(), assistedFullName = "${binding?.edtLoginName?.text.toString().trim()} ${
                binding?.edtLoginSurname?.text.toString().trim()}", onSubmitClickListener = { dialog, bitmap ->
                signBitmap = bitmap
             /*   binding?.btnPurchaseShowList?.apply {
                    isEnabled = false
                    alpha = 0.5F
                }*/
                getLocation()
                dialog?.dismiss()

            }, onCancelClickListener = {
                it?.dismiss()
            }).show(childFragmentManager, "ConfirmDialogFragment")
        }

     /*   binding?.pluse?.setOnClickListener {

            if (num < 20) {
                num++

                binding?.variable?.text = num.toString()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Maxsimum 20 adet dağıtım alınabilir.",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }*/


     /*   binding?.minus?.setOnClickListener {

            if (num > 1) {
                num--

                binding?.variable?.text = num.toString()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Minimum 1 adet dağıtım alınabilir.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }*/

       /* binding?.btnPurchaseShowList?.setOnClickListener{
            findNavController().navigate(R.id.action_purchaseFragment_to_listFragment, bundleOf("VolunteerID" to volunteerID))
        }*/


        return binding?.root
    }

    private fun distribution(
        TCID: String?,
        Name: String?,
        SurName: String?,
        Coordinate: String?,
        PackageCount: String?,
        Volunteer: String?,
        City: String?,
        ImzaBinary : String?
    ) {
        purchaseVİewModel.distribution(TCID = TCID, Name = Name, SurName = SurName, Coordinate = Coordinate, PackageCount = PackageCount, Volunteer = Volunteer, City = City, ImzaBinary)
            .observe(viewLifecycleOwner) {
              /*  binding?.btnPurchaseShowList?.apply {
                    isEnabled = true
                    alpha = 1F
                }*/

                it?.let { globalResponse ->
                    when(globalResponse.status) {

                        ResponseStatus.SUCCESS -> {
                            globalResponse.data?.let {

                                if (it.Message != null && it.Message != "OK") {
                                    Toast.makeText(
                                        requireContext(),
                                        it.Message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if(it.Message != null && it.Message == "OK") {
                                    Toast.makeText(
                                        requireContext(),
                                        "Dağıtım bilgisi başarıyla alındı.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    requireActivity().onBackPressed()
                                }

                            }
                        }

                        ResponseStatus.ERROR -> {
                            Toast.makeText(
                                requireContext(),
                                "Kullanıcı Bilgileri Geçersiz.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
    }

    private fun getLocation() {
        val hasPermission =
            requireContext().hasPermission()
        if (hasPermission) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                }).addOnSuccessListener { location ->
                if (location != null) {

                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val cityName: String =  if (addresses != null && addresses.size > 0 && addresses[0].adminArea != null) {
                        addresses[0].adminArea
                    } else {
                        ""
                    }


                    distribution(
                        TCID = userId,
                        Name = binding?.edtLoginName?.text.toString().trim(),
                        SurName = binding?.edtLoginSurname?.text.toString().trim(),
                        Coordinate = "${location.latitude},${location.longitude}",
                        PackageCount = binding?.variable?.text.toString(),
                        Volunteer = volunteerID,
                        City = cityName,
                        ImzaBinary = getBase64Bitmap(signBitmap)
                    )
                }

            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Konum izinlerini kontrol ediniz.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
       /*
        0.7020602218700475 - 443 - 631
        0.702991452991453 - 658 -936

        */
    }




}
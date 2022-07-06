package com.halasteknoloji.meckurbanyonetim.fragment

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.halasteknoloji.meckurbanyonetim.R
import com.halasteknoloji.meckurbanyonetim.activities.hasPermission
import com.halasteknoloji.meckurbanyonetim.activities.showSoftKeyboard
import com.halasteknoloji.meckurbanyonetim.databinding.FragmentQueryBinding

class QueryFragment : Fragment() {


    private var binding: FragmentQueryBinding? = null

    var volunteerID: String? = null
    var volunteerFullName: String? = null
    var city: String? = null

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            it.map {

                it.value == false

            }.lastOrNull().also {

                if (it != false) {

                    Toast.makeText(
                        requireContext(),
                        "Lütfen Konum İzni Veriniz.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    findNavController().navigate(
                        R.id.action_queryFragment_to_purchaseFragment, bundleOf(
                            "userId" to binding?.edtLoginEmail?.text.toString(),
                            "volunteerID" to volunteerID,
                            "city" to city,
                            "volunteerFullName" to "$volunteerFullName"
                        )
                    )

                    binding?.edtLoginEmail?.text?.clear()

                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentQueryBinding.inflate(inflater, container, false)

        volunteerID = arguments?.getString("volunteerID")
        city = arguments?.getString("city")
        volunteerFullName = arguments?.getString("volunteerFullName")

        binding?.edtLoginEmail?.requestFocus()
        binding?.edtLoginEmail?.let { showSoftKeyboard(it, requireContext()) }

        binding?.btnLoginEnter?.setOnClickListener {

            if ((binding?.edtLoginEmail?.text?.length ?: 0) < 11) {
                Toast.makeText(
                    requireContext(),
                    "Lütfen Geçerli Bir Tc Kimlik Numarası Giriniz!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val hasPermission = requireContext().hasPermission()

                if (!hasPermission) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                } else {


                    findNavController().navigate(
                        R.id.action_queryFragment_to_purchaseFragment, bundleOf(
                            "userId" to binding?.edtLoginEmail?.text.toString(),
                            "volunteerID" to volunteerID,
                            "city" to city,
                            "volunteerFullName" to "$volunteerFullName"
                        )
                    )

                    binding?.edtLoginEmail?.text?.clear()
                }

            }
        }

        binding?.edtLoginEmail?.addTextChangedListener {
            binding?.btnLoginEnter?.isEnabled = (it?.length ?: 0) <= 11
        }

        binding?.btnPurchaseShowList?.setOnClickListener{
            findNavController().navigate(R.id.action_queryFragment_to_listFragment, bundleOf("VolunteerID" to volunteerID))
        }

        return binding?.root

    }

}
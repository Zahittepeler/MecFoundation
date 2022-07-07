package com.halasteknoloji.meckurbanyonetim.fragment.login

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.halasteknoloji.meckurbanyonetim.R
import com.halasteknoloji.meckurbanyonetim.activities.getBase64BitmapPNG
import com.halasteknoloji.meckurbanyonetim.activities.hasPermission
import com.halasteknoloji.meckurbanyonetim.activities.showSoftKeyboard
import com.halasteknoloji.meckurbanyonetim.databinding.FragmentLoginBinding
import com.halasteknoloji.meckurbanyonetim.fragment.sign.SignFragment
import com.halasteknoloji.meckurbanyonetim.models.ResponseStatus
import com.halasteknoloji.meckurbanyonetim.utils.LocalDataManager


class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null


    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val userName = LocalDataManager.getPreferencesStrVal("UserName")
        val userPassword = LocalDataManager.getPreferencesStrVal("UserPassword")

        if (userName != null) {
            binding?.edtLoginEmail?.setText(userName)
        }

        if (userPassword != null) {
            binding?.edtLoginPassword?.setText(userPassword)
        }

        binding?.edtLoginEmail?.requestFocus()
        binding?.edtLoginEmail?.let { showSoftKeyboard(it, requireContext()) }

        binding?.btnLoginEnter?.setOnClickListener {

            val loginUser = binding?.edtLoginEmail?.text.toString()
            val loginPassword = binding?.edtLoginPassword?.text.toString()

            if ((loginUser.isNullOrEmpty()) && (loginPassword.isNullOrEmpty())) {

                Toast.makeText(requireContext(), "Giriş bilgilerini giriniz.", Toast.LENGTH_LONG).show()
            } else {
                loginUser(loginUser, loginPassword)
            }

        }


        return binding?.root
    }


    private fun loginUser(UserName: String?, Password: String?) {
        loginViewModel.loginUser(UserName, Password).observe(viewLifecycleOwner) {
            it?.let { globalResponse ->
                when (globalResponse.status) {

                    ResponseStatus.SUCCESS -> {
                        globalResponse.data?.let {

                            if (it.Id != null && it.ChangePassword == false) {


                                    SignFragment(true, onSubmitClickListener = { dialogF, bitmap ->

                                        if (bitmap != null) {
                                            val emptyBitmap = Bitmap.createBitmap(
                                                bitmap.getWidth(),
                                                bitmap.getHeight(),
                                                bitmap.getConfig()
                                            )

                                            if (!bitmap.sameAs(emptyBitmap)) {
                                                LocalDataManager.setPreferences(
                                                    it.Id.toString(),
                                                    getBase64BitmapPNG(bitmap)
                                                )

                                                LocalDataManager.setPreferences("UserName", UserName)
                                                LocalDataManager.setPreferences("UserPassword", Password)

                                                findNavController().navigate(
                                                    R.id.action_loginFragment_to_queryFragment,
                                                    bundleOf(
                                                        "volunteerID" to it.Id,
                                                        "city" to it.City,
                                                        "volunteerFullName" to "${it.FullName}"
                                                    )
                                                )
                                            } else {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Lütfen İmza Ekleyiniz",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                        dialogF?.dismiss()

                                    }, onCancelClickListener = {
                                        Toast.makeText(
                                            requireContext(),
                                            "Lütfen İmza Ekleyiniz",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        it?.dismiss()
                                    }).show(childFragmentManager, SignFragment::class.java.name)




                            } else if(it.ChangePassword == true) {
                                binding?.edtLoginPassword?.text?.clear()
                                findNavController().navigate(
                                    R.id.action_loginFragment_to_passwordResetFragment, bundleOf("volunteerID" to it.Id))

                            }  else {
                                Toast.makeText(
                                    requireContext(),
                                    "kullanıcı bulunamadı veya parolanız yanlış",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }

                    ResponseStatus.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "kullanıcı bulunamadı veya parolanız yanlış",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        loginViewModel.loginUser("","").removeObservers(viewLifecycleOwner)
        super.onDestroyView()
    }


}
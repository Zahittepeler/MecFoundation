package com.halasteknoloji.meckurbanyonetim.fragment.passwordReset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.halasteknoloji.meckurbanyonetim.R
import com.halasteknoloji.meckurbanyonetim.databinding.FragmentPasswordResetBinding
import com.halasteknoloji.meckurbanyonetim.models.ResponseStatus
import com.halasteknoloji.meckurbanyonetim.utils.LocalDataManager
import java.util.*
import java.util.regex.Pattern

class PasswordResetFragment : Fragment() {


    private var binding: FragmentPasswordResetBinding? = null
    private val passwordResetViewModel: PasswordResetViewModel by viewModels()
    private var volunteerID: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        binding = FragmentPasswordResetBinding.inflate(inflater, container, false)
        volunteerID = arguments?.getString("volunteerID")


        binding?.btnPasswordResetReset?.setOnClickListener{

            binding?.apply {
                if (edtPasswordResetNewPassword.text.isNullOrEmpty() || edtPasswordResetOldPassword.text.isNullOrEmpty() || edtPasswordResetPasswordConfirm.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Lütfen boş alanları doldurunuz",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!isPasswordValid(edtPasswordResetNewPassword.text.toString().trim())) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.btn_passwordResetFragment_passwordHelper),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (edtPasswordResetNewPassword.text.toString().trim() != edtPasswordResetPasswordConfirm.text.toString().trim()) {
                    Toast.makeText(
                        requireContext(),
                        "Lütfen aynı şifreyi giriniz",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    resetPassword()
                }
            }
        }

        return binding?.root
    }

    private fun resetPassword() {
        passwordResetViewModel.resetPassword(volunteerID, binding?.edtPasswordResetNewPassword?.text.toString(), binding?.edtPasswordResetOldPassword?.text.toString(), true).observe(viewLifecycleOwner) {
            it?.let { globalResponse ->
                when (globalResponse.status) {

                    ResponseStatus.SUCCESS -> {
                        globalResponse.data?.let {

                            if (it.Message.toString().equals("OK")) {
                                LocalDataManager.setPreferences("UserPassword", null)
                                findNavController().popBackStack()
                            }  else {
                                Toast.makeText(
                                    requireContext(),
                                    it.Message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }

                    ResponseStatus.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "Kullanıcı Bilgileri Geçersiz",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun isPasswordValid(email: String?): Boolean {
        val expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,8}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email ?: "")
        return matcher.matches()
    }

}
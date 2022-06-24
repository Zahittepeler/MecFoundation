package com.erlab.mecfoundation.activities.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.erlab.mecfoundation.R
import com.erlab.mecfoundation.activities.MainActivity
import com.erlab.mecfoundation.activities.models.User
import com.erlab.mecfoundation.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    //Note:

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding?.btnLoginEnter?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_queryFragment)
        }

        val sharedPreferences = requireActivity().getSharedPreferences("FEYZUL_FURKAN_PREFERENCES",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()


        binding?.btnLoginEnter?.setOnClickListener {

            val user = User("deneme", "deneme", "deneme")
            val mail = user.eMail
            val password = user.password

            val loginUser = binding?.edtLoginEmail?.text
            val loginPassword = binding?.edtLoginPassword?.text


            if ((loginUser.isNullOrEmpty()) && (loginPassword.isNullOrEmpty())){

                Toast.makeText(requireContext(),"Giriş bilgilerini giriniz", Toast.LENGTH_LONG).show()


            } else if ((mail == loginUser.toString()) && (password == loginPassword.toString())){

                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()

            } else {

                Toast.makeText(requireContext(),"Giriş bilgileri hatalı", Toast.LENGTH_LONG).show()

            }


        }




        return binding?.root
    }

}
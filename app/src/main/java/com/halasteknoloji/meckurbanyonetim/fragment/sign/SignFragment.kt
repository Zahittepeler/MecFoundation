package com.halasteknoloji.meckurbanyonetim.fragment.sign

import android.app.Dialog
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.halasteknoloji.meckurbanyonetim.R
import com.halasteknoloji.meckurbanyonetim.activities.loadBitmapFromView
import com.halasteknoloji.meckurbanyonetim.databinding.FragmentSignBinding


class SignFragment(private val isLoginFragment: Boolean = false, private val onSubmitClickListener: ((dialog: SignFragment?, b: Bitmap?) -> Unit)? = null, private val onCancelClickListener: ((dialog: SignFragment?) -> Unit)? = null) : DialogFragment() {


    private var binding: FragmentSignBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        binding?.drawPencil?.setPath(path)

        if (isLoginFragment) {
            binding?.apply {
                btnDialogNoButton.isVisible = false
                btnDialogYesButton.text = getString(R.string.create_sign_button)
                txtLayoutDialogInfo.isVisible = true
            }
        }

        binding?.btnDialogYesButton?.setOnClickListener {
            val b = binding?.drawPencil?.let { loadBitmapFromView(it) }
            onSubmitClickListener?.invoke(this, b)
        }

        binding?.btnDialogNoButton?.setOnClickListener {

            onCancelClickListener?.invoke(this)
        }

        binding?.txtLayoutDialogClear?.setOnClickListener {
            binding?.drawPencil?.undo()
        }
        return binding?.root
    }

    override fun onDestroyView() {

       binding?.drawPencil?.undo()
        super.onDestroyView()
    }


    companion object {
        var path = Path()
    }

}
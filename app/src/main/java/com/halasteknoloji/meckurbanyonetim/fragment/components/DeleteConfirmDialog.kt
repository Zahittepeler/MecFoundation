package com.halasteknoloji.meckurbanyonetim.fragment.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.halasteknoloji.meckurbanyonetim.databinding.LayoutDeleteConfirmDialogBinding

class DeleteConfirmDialog(private val onSubmitClickListener: ((dialog: Dialog?) -> Unit)? = null, private val onCancelClickListener: ((dialog: Dialog?) -> Unit)? = null) : DialogFragment() {


    private var binding: LayoutDeleteConfirmDialogBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = LayoutDeleteConfirmDialogBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        dialog?.setCanceledOnTouchOutside(false)


        binding?.btnDialogYesButton?.setOnClickListener {
            onSubmitClickListener?.invoke(dialog)
        }

        binding?.btnDialogNoButton?.setOnClickListener {

            onCancelClickListener?.invoke(dialog)
        }


        return binding?.root
    }

}
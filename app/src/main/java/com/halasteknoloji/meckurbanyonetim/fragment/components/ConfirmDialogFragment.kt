package com.halasteknoloji.meckurbanyonetim.fragment.components

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Base64.decode
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.halasteknoloji.meckurbanyonetim.activities.loadBitmapFromView
import com.halasteknoloji.meckurbanyonetim.databinding.LayoutDialogBinding
import com.halasteknoloji.meckurbanyonetim.fragment.sign.SignFragment
import com.halasteknoloji.meckurbanyonetim.utils.LocalDataManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ConfirmDialogFragment(
    val volunteerID: String?,
    val count: String,
    val volunteerFullName: String,
    val assistedFullName: String,
    private val onSubmitClickListener: ((dialog: Dialog?, b: Bitmap?) -> Unit)? = null,
    private val onCancelClickListener: ((dialog: Dialog?) -> Unit)? = null
) : DialogFragment() {


    private var binding: LayoutDialogBinding? = null
    private var signBitmap: Bitmap? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = LayoutDialogBinding.inflate(LayoutInflater.from(context))


        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding?.root)

        binding?.btnDialogYesButton?.setOnClickListener {

            onSubmitClickListener?.invoke(dialog, signBitmap)

        }

        binding?.btnDialogNoButton?.setOnClickListener {

            onCancelClickListener?.invoke(dialog)
        }

        volunteerID?.apply {
            val imageAsBytes: ByteArray =
                decode(LocalDataManager.getPreferencesStrVal(this), Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
            binding?.imgLayoutDialogSignVolunteer?.setImageBitmap(bitmap)
        }


        binding?.apply {
            txtLayoutDialogCount.text = count
            txtLayoutDialogVolunteer.text = volunteerFullName
            txtLayoutDialogAssisted.text = assistedFullName
            txtLayoutDialogDate.text = getDateFormat()
        }

        binding?.btnDialogSign?.setOnClickListener {
            SignFragment(onSubmitClickListener = { dialogF, bitmap ->

                if (bitmap != null) {

                    val emptyBitmap = Bitmap.createBitmap(
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        bitmap.getConfig()
                    )

                    if (!bitmap.sameAs(emptyBitmap)) {
                        binding?.btnDialogYesButton?.apply {
                            alpha = 1F
                            isEnabled = true
                        }
                    }

                    binding?.imgLayoutDialogSign?.setImageBitmap(bitmap)

                    signBitmap = binding?.clDialogReceipt?.let { it1 -> loadBitmapFromView(it1) }
                } else {
                    signBitmap = null
                }

                dialogF?.dismiss()

            }, onCancelClickListener = {
                signBitmap = null
                it?.dismiss()
            }).show(childFragmentManager, SignFragment::class.java.name)
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun getDateFormat(): String {

        var finalDateString: String = ""
        try {
            val sdfnewformat = SimpleDateFormat("MM/DD/yyyy")
            finalDateString = sdfnewformat.format(Date())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return finalDateString
    }

}
package com.halasteknoloji.meckurbanyonetim.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import java.io.ByteArrayOutputStream


fun Context.hasPermission(): Boolean {

    return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}


fun getBase64Bitmap(signBitmap: Bitmap?): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    signBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream .toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}


fun getBase64BitmapPNG(signBitmap: Bitmap?): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    signBitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream .toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}


fun loadBitmapFromView(v: View): Bitmap? {
    val b = Bitmap.createBitmap(
        v.measuredWidth,
        v.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    val c = Canvas(b)
    v.layout(v.left, v.top, v.right, v.bottom)
    v.draw(c)
    return b
}

fun showSoftKeyboard(view: View, context: Context) {
    if (view.requestFocus()) {
        val imm: InputMethodManager? =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

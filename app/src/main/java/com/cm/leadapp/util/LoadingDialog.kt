package com.cm.leadapp.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.cm.kbslead.R

class LoadingDialog(private var activity: Activity?) {

    private var alertDialog: AlertDialog? = null

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(
            activity!!
        )
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.layout_progress, null))
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.show()
    }

    fun dismissDialog() {
        alertDialog?.dismiss()
    }
}
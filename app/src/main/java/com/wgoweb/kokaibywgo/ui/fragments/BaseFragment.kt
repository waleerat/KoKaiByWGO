package com.wgoweb.kokaibywgo.ui.fragments

import android.app.Dialog
import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.DialogProgressBinding


open class BaseFragment : Fragment() {

    private lateinit var dialogBinding : DialogProgressBinding

    private lateinit var mProgressDialog: Dialog

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())

        dialogBinding = DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog.setContentView(dialogBinding.root)

        dialogBinding.tvProgressText.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }



}
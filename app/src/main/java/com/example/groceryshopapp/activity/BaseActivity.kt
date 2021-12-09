package com.example.groceryshopapp.activity

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopapp.R


import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.groceryshopapp.databinding.ActivityBaseBinding
import com.example.groceryshopapp.databinding.CustomProgressDialogBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import java.security.AccessController.getContext


open class BaseActivity : AppCompatActivity() {

    private var doubleTaptoExit = false
    private var mProgressDialog: Dialog? = null
    private var binding: ActivityBaseBinding? = null

    private var dialogBinding: CustomProgressDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    public fun showProgressDialog(text: String) {

        mProgressDialog = Dialog(this)

        dialogBinding = CustomProgressDialogBinding.inflate(
            LayoutInflater.from(getApplicationContext()), null, false
        )

        dialogBinding?.txtProgressBar?.setText(text)


        mProgressDialog?.setContentView(dialogBinding?.getRoot() as View)

        mProgressDialog?.show()

    }

    public fun hideProgressBar() {
        mProgressDialog?.dismiss()
        dialogBinding = null
    }


    private fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid

    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

    fun doubleBackToExit() {
        if (doubleTaptoExit) {
            super.onBackPressed()
            return
        }

        doubleTaptoExit = true
        Toast.makeText(this, R.string.click_once, Toast.LENGTH_LONG).show()
    }

    fun showErrorSnack(message: String, view: View) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

        snackbar.show()
    }
}
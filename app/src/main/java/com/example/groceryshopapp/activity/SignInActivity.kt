package com.example.groceryshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.groceryshopapp.R
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivitySignInBinding
import com.example.groceryshopapp.utils.Constants
import com.example.groceryshopapp.utils.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {

    private var binding: ActivitySignInBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_sign_in)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setonClickListener()

        setActionBar()
    }


    private fun setActionBar() {
        setSupportActionBar(binding?.toolbarSigninActivity)
        var supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.signin))
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setonClickListener() {
        binding?.btnSignUp?.setOnClickListener {

            var email = binding?.txetEmailid?.text.toString()
            var password = binding?.txetPassword?.text.toString()


            when {
                !TextUtils.isValidEmail(email) -> {
                    showErrorSnack(getString(R.string.enter_email), it)

                }
                password.isEmpty() -> {
                    showErrorSnack(getString(R.string.enter_password), it)


                }
                else -> {
                    showProgressDialog(getString(R.string.signing_in))

                    FirestoreClass().signInUser(this, email, password)


                }

            }

        }
    }

    fun onSignInCompleteListner(task: Task<AuthResult>) {

        if (task.isSuccessful) {
            hideProgressBar()

            val firebaseUser = task.result!!.user!!;
            val registeredEmail = firebaseUser.email!!
            Toast.makeText(this, "hello ${firebaseUser.displayName}", Toast.LENGTH_LONG).show()

            var intent = Intent(this@SignInActivity, DisplayActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            hideProgressBar()
            Toast.makeText(this, Constants.SIGN_IN_FAILED, Toast.LENGTH_LONG).show()
        }

    }
}
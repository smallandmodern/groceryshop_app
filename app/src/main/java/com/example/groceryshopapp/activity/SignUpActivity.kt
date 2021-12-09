package com.example.groceryshopapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.groceryshopapp.R
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivitySignUpBinding
import com.example.groceryshopapp.models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : BaseActivity() {
    private var binding: ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar()

        binding?.btnSignUp?.setOnClickListener {
            registerUser(it)
        }

    }

    override fun onBackPressed() {
        finish()
    }

    private fun registerUser(view: View) {

        var userName = binding?.txetName?.text.toString()
        var email = binding?.txetEmailid?.text.toString()
        var password = binding?.txetPassword?.text.toString()


        if (validateForm(userName, email, password, view)) {

            showProgressDialog("Registering user")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // hideProgressBar()
                        val firebaseUser = task.result!!.user!!;
                        val registeredEmail = firebaseUser.email!!
                        // Toast.makeText(this,"user registered with $registeredEmail",Toast.LENGTH_LONG).show()
                        val user = UserModel(firebaseUser.uid, firebaseUser.email!!, userName)

                        FirestoreClass().regisrterUser(this, user)
                        //FirebaseAuth.getInstance().signOut()


                    } else {
                        hideProgressBar()
                        Toast.makeText(this, "user registeration failed ", Toast.LENGTH_LONG).show()
                    }

                }

        }
    }

    private fun setSupportActionBar() {

        setSupportActionBar(binding?.toolbarSigninActivity)
        var supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            supportActionBar.setTitle(getString(R.string.signup))

        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


        return super.onOptionsItemSelected(item)
    }

    private fun validateForm(
        userName: String,
        email: String,
        password: String,
        view: View
    ): Boolean {

        when {
            userName.isEmpty() -> {
                showErrorSnack(getString(R.string.enter_username), view)
                return false
            }
            email.isEmpty() -> {
                showErrorSnack(getString(R.string.enter_email), view)
                return false
            }
            password.isEmpty() -> {
                showErrorSnack(getString(R.string.enter_password), view)
                return false

            }
            else -> return true

        }

        return true

    }

    fun onUserRegistrationCompleteLisner(task: Task<Void>) {

        if (task.isSuccessful) {
            Toast.makeText(this, "you have successfully registered", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "user registeration failed ", Toast.LENGTH_LONG).show()
        }
        hideProgressBar()
        finish()
    }
}
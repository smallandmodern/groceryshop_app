package com.example.groceryshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.groceryshopapp.R
import com.example.groceryshopapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setonClickListners()

    }

    private fun setonClickListners() {
        binding?.btnSignIn?.setOnClickListener(View.OnClickListener {

            var intent = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(intent)

        })

        binding?.btnSignUp?.setOnClickListener(View.OnClickListener {

            var intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
            // finish()
        })
    }
}
package com.example.groceryshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {


    private var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        CoroutineScope(Dispatchers.Main).launch {

            delay(3000)

            if (FirestoreClass().getCurrentUserId().isEmpty()) {
                var intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)

            } else {
                var intent = Intent(this@SplashActivity, DisplayActivity::class.java)
                startActivity(intent)

            }
            finish()
        }
    }
}
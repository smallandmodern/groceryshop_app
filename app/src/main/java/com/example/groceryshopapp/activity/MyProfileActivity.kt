package com.example.groceryshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopapp.databinding.ActivityMyProfileBinding

class MyProfileActivity : AppCompatActivity() {

    private var binding: ActivityMyProfileBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}
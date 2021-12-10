package com.example.groceryshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groceryshopapp.R
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {


    private var binding:ActivityOrderHistoryBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setActionBar()
        getOrderData()
    }

    private fun getOrderData() {
        //FirestoreClass().getOrderHistory(this)
    }

    private fun setActionBar() {
        setSupportActionBar(binding?.toolbarOrderHistoryActivity)
        var supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.order_history))
        }
    }
}
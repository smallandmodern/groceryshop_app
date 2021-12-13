package com.example.groceryshopapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.groceryshopapp.R
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivityOrderHistoryBinding
import com.example.groceryshopapp.models.CategoryModel
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.models.OrderModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

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
        FirestoreClass().getOrderHistory(this)
    }

    private fun setActionBar() {
        setSupportActionBar(binding?.toolbarOrderHistoryActivity)
        var supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.order_history))
        }
    }

    fun onGetOrderHistory(task: Task<DocumentSnapshot>) {
        if (task.isSuccessful) {
            val orderList: ArrayList<OrderModel> = ArrayList()

            task.result?.data?.forEach {

                var orderModel=OrderModel()

                orderModel.orderlist=it.value as ArrayList<GroceryModel>
                orderModel.documentID= task?.result?.id.toString()

            }
            if(task?.result?.data?.isNotEmpty() == true)
            {
                for( i in task?.result?.data!!)
                {

                }

            }

        }



    }
}
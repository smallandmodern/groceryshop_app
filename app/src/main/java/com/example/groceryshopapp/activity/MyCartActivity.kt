package com.example.groceryshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.groceryshopapp.R
import com.example.groceryshopapp.adapters.GroceryCartListAdapter
import com.example.groceryshopapp.adapters.GroceryListAdapter
import com.example.groceryshopapp.databinding.ActivityMyCartBinding
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.utils.Constants

class MyCartActivity : BaseActivity(), GroceryCartListAdapter.RemoveFromCartClickListner {

    private var binding: ActivityMyCartBinding? = null
    private var myCartList: ArrayList<GroceryModel>? = null

    private var totalPrice: Double = 0.0

    private lateinit var cartAdapter: GroceryCartListAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        myCartList =
            intent.getSerializableExtra(Constants.MY_CART_GROCERY_LIST) as ArrayList<GroceryModel>?

        setActionBar()
        setadapterData()
        setPrice()
    }

    private fun setActionBar() {
        setSupportActionBar(binding?.toolbarMycartActivity)
        var supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.my_cart))
        }
    }

    private fun setPrice() {

        totalPrice=0.0
        myCartList?.forEach {
            totalPrice += (it.price*it.orderedCount)

        }
        binding?.txtPrice?.text = " € ${totalPrice.toString()}"
    }

    private fun setadapterData() {

        if (!myCartList.isNullOrEmpty()) {
            cartAdapter = GroceryCartListAdapter(myCartList as ArrayList<GroceryModel>)
            cartAdapter.setRemoveFromCartClickListner(this)
            binding?.rvCart?.adapter = cartAdapter
            binding?.txtNoData?.visibility = View.GONE
            binding?.rvCart?.visibility = View.VISIBLE
            binding?.txtPrice?.visibility = View.VISIBLE
        } else {
            binding?.txtPrice?.visibility = View.VISIBLE
            binding?.txtNoData?.visibility = View.VISIBLE
            binding?.rvCart?.visibility = View.GONE
        }


    }

    override fun onBackPressed() {


        val intent = Intent()
        intent.putExtra(Constants.MY_CART_GROCERY_LIST,myCartList)
        setResult(RESULT_OK, intent)
        finish()

       // intent.putExtra(Constants.MY_CART_GROCERY_LIST,myCartList)

       // finish()
    }

    override fun onclick(position: Int, gorceryItem: GroceryModel) {

        myCartList?.removeAt(position)
        cartAdapter.notifyDataSetChanged()

        if (myCartList.isNullOrEmpty()) {
            binding?.txtNoData?.visibility = View.VISIBLE
            binding?.txtPrice?.visibility = View.VISIBLE
            binding?.rvCart?.visibility = View.GONE
        }
        setPrice()

    }


}
package com.example.groceryshopapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.groceryshopapp.R
import com.example.groceryshopapp.adapters.GroceryCartListAdapter
import com.example.groceryshopapp.adapters.GroceryListAdapter
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivityMyCartBinding
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.utils.Constants

class MyCartActivity : BaseActivity(), GroceryCartListAdapter.RemoveFromCartClickListner,GroceryCartListAdapter.MinusButtonClickListner,GroceryCartListAdapter.PlusButtonClickListner {

    private var binding: ActivityMyCartBinding? = null
    private lateinit var myCartList: ArrayList<GroceryModel>

    private var totalPrice: Double = 0.0

    private lateinit var cartAdapter: GroceryCartListAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        myCartList =
            (intent.getSerializableExtra(Constants.MY_CART_GROCERY_LIST) as ArrayList<GroceryModel>?)!!

        setActionBar()
        setadapterData()
        setPrice()

        binding?.btnPlaceOrder?.setOnClickListener {

            FirestoreClass().updateGroceryCountList(this,myCartList)

            FirestoreClass().saveOrderHistory(this,myCartList)
            val intent = Intent()
            intent.putExtra(Constants.MY_CART_GROCERY_LIST,myCartList)
            setResult(RESULT_OK, intent)
            finish()

        }
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
            cartAdapter.setMinusButtonClickListner(this)
            cartAdapter.setPlusButtonClickListner(this)
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
        finish()
    }

    override fun onclick(position: Int) {

        myCartList?.removeAt(position)
        cartAdapter.notifyDataSetChanged()

        if (myCartList.isNullOrEmpty()) {
            binding?.txtNoData?.visibility = View.VISIBLE
            binding?.txtPrice?.visibility = View.VISIBLE
            binding?.rvCart?.visibility = View.GONE
        }
        setPrice()

    }

    override fun onclickPlusClick(position: Int, gorceryItem: GroceryModel) {

       if(myCartList?.get(position)?.orderedCount!! <= myCartList?.get(position)?.count!!)
       {
           myCartList?.get(position)?.orderedCount=myCartList?.get(position)?.orderedCount!!+1

           myCartList?.get(position)?.count=myCartList?.get(position)?.count!!-1
       }else
       {
           Toast.makeText(this,Constants.ITEM_OUT_OF_STOCK,Toast.LENGTH_LONG).show()
       }

        cartAdapter.notifyDataSetChanged()
        setPrice()
    }

    override fun onclickMinusClick(position: Int, gorceryItem: GroceryModel) {
        if(myCartList?.get(position)?.orderedCount!! > 1)
        {
            myCartList?.get(position)?.orderedCount=myCartList?.get(position)?.orderedCount!!-1
            myCartList?.get(position)?.count=myCartList?.get(position)?.count!!+1
        }else
        {
            onclick(position)
        }

        cartAdapter.notifyDataSetChanged()
        setPrice()
    }


}
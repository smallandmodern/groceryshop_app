package com.example.groceryshopapp.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryshopapp.R
import com.example.groceryshopapp.adapters.GroceryListAdapter
import com.example.groceryshopapp.database.FirestoreClass
import com.example.groceryshopapp.databinding.ActivityDisplayBinding
import com.example.groceryshopapp.models.CategoryModel
import com.example.groceryshopapp.models.GroceryModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.groceryshopapp.databinding.NavHeaderMainBinding
import com.example.groceryshopapp.models.UserModel
import com.example.groceryshopapp.utils.Constants
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot


class DisplayActivity : BaseActivity(), GroceryListAdapter.AddToCartClickListner,
    NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityDisplayBinding? = null

    private var groceryCartList = ArrayList<GroceryModel>()
    private var grocerylist = ArrayList<GroceryModel>()
    private lateinit var myCart: TextView
    private lateinit var adapterGroceryList: GroceryListAdapter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.includedAppbar?.toolbarDisplayActivity)
        binding?.navView?.setNavigationItemSelectedListener(this)
        setActionBar()
        fetchData()
        fetchGroceryListData()
    }

    private fun fetchGroceryListData() {
        FirestoreClass().getGroceryList(this);
    }


    private fun fetchData() {

        FirestoreClass().getCateryList(this);
        FirestoreClass().getUserDetails(this);
    }

    private fun setActionBar() {
        var supportActionBar = getSupportActionBar()
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }



        myCart = binding?.navView?.menu?.findItem(R.id.item_my_cart)?.actionView as TextView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                menuButtonPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if (binding?.drlLayout?.isDrawerOpen(GravityCompat.START) == true) {
            binding?.drlLayout?.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
    }

    fun onGetCatListOnSuccessListner(task: Task<QuerySnapshot>) {
        if (task.isSuccessful) {
            val catlist: ArrayList<CategoryModel> = ArrayList()
            if (task?.result?.documents?.isEmpty() == false) {

                for (i in task?.result?.documents!!) {
                    var cat = i.toObject(CategoryModel::class.java)
                    if (cat != null) {
                        catlist.add(cat)
                    }

                }

            }
            print("CatList" + catlist)
        } else {
            Toast.makeText(this, "api failed", Toast.LENGTH_LONG).show()
        }


    }

    fun onGetGroceryListOnSuccessListner(task: Task<QuerySnapshot>) {
        if (task.isSuccessful) {


            if (task?.result?.documents?.isEmpty() == false) {

                for (i in task?.result?.documents!!) {
                    var gorceryModel = i.toObject(GroceryModel::class.java)
                    if (gorceryModel != null) {
                        grocerylist.add(gorceryModel)
                    }
                }
            }
            print("groceryList" + grocerylist)
            adapterGroceryList = GroceryListAdapter(grocerylist)
            (findViewById(R.id.rv_grocerylist) as RecyclerView).adapter = adapterGroceryList
            adapterGroceryList.setAddToCartClickListner(this)


        } else {
            Toast.makeText(this, "api failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun menuButtonPressed() {
        if (binding?.drlLayout?.isDrawerOpen(GravityCompat.START) == true) {
            binding?.drlLayout?.closeDrawer(GravityCompat.START)
        } else {
            binding?.drlLayout?.openDrawer(GravityCompat.START)
        }
    }

    override fun onclick(position: Int, gorceryItem: GroceryModel) {
        if (groceryCartList.contains(gorceryItem)) {
            groceryCartList[groceryCartList.indexOf(gorceryItem)].orderedCount++
            // groceryCartList[groceryCartList.indexOf(gorceryItem)].count--
            grocerylist[grocerylist.indexOf(gorceryItem)].count--
            adapterGroceryList.notifyDataSetChanged()
        } else {
            gorceryItem.orderedCount = 1
            //gorceryItem.count--
            grocerylist[grocerylist.indexOf(gorceryItem)].count--
            adapterGroceryList.notifyDataSetChanged()
            groceryCartList.add(gorceryItem)
        }
        myCart.setGravity(Gravity.CENTER_VERTICAL);
        myCart.setTypeface(null, Typeface.BOLD);
        myCart.setText(groceryCartList.size.toString());
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_my_cart -> {
                if (groceryCartList.isEmpty()) {
                    Toast.makeText(this, getString(R.string.no_item_in_cart), Toast.LENGTH_LONG)
                        .show()
                } else {
                    var intent = Intent(this@DisplayActivity, MyCartActivity::class.java)
                    intent.putExtra(Constants.MY_CART_GROCERY_LIST, groceryCartList)
                    startForResult.launch(intent)
                }
            }
            R.id.item_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                Intent(this@DisplayActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.item_my_profile -> {
                // startActivity(Intent(this@DisplayActivity, MyProfileActivity::class.java))
            }
            R.id.item_order_history -> {
                startActivity(Intent(this@DisplayActivity, OrderHistoryActivity::class.java))

            }
        }

        binding?.drlLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    fun onUserDetailsCompleteListner(task: Task<DocumentSnapshot>) {


        if(task.isSuccessful)
        {

            var userModel=(task?.result as DocumentSnapshot).toObject(UserModel::class.java)


       val headerView: View = binding?.navView?.getHeaderView(0) as View
        val headerBinding: NavHeaderMainBinding = NavHeaderMainBinding.bind(headerView)
            headerBinding.txtUserName.text="Hello ${userModel?.name?.uppercase()}"

        }

    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                    groceryCartList= ArrayList<GroceryModel>()
                    myCart.setText(groceryCartList.size.toString());
            }
        }

    override fun onResume() {
        super.onResume()
        //fetchGroceryListData()

    }

}



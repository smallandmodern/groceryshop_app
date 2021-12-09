package com.example.groceryshopapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groceryshopapp.activity.DisplayActivity
import com.example.groceryshopapp.databinding.ItemGroceryListBinding
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.utils.Constants

class GroceryListAdapter(var groceryList: List<GroceryModel>) :
    RecyclerView.Adapter<GroceryListAdapter.ViewHolder>() {

    private var addToCartClickListner: AddToCartClickListner? = null

    fun setAddToCartClickListner(clickListner: AddToCartClickListner) {
        this.addToCartClickListner = clickListner
    }

    class ViewHolder(binding: ItemGroceryListBinding) : RecyclerView.ViewHolder(binding?.root) {

        var txtGroceryName = binding.txtGroceryName
        var imgGrocery = binding.imgGrocery
        var txtGroceryPrice = binding.txtGroceryPrice
        var txtGroceryWeight = binding.txtGroceryWeight
        var btnAddToCart = binding.btnAddToCart

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemGroceryListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            holder.txtGroceryName.text = groceryList[position].name
            holder.txtGroceryWeight.text = groceryList[position].weight
           //holder.txtGroceryPrice.text = "€ ${groceryList[position].price}"
            holder.txtGroceryPrice.text = "€ "+groceryList[position].price.toString()

            if (groceryList[position].count > 0) {
                holder.btnAddToCart.setText(Constants.ADD_TO_CART)
                holder.btnAddToCart.setOnClickListener(View.OnClickListener {
                    addToCartClickListner?.onclick(position, groceryList[position])
                })
            } else {
                holder.btnAddToCart.setText(Constants.OUT_OF_STOCK)
            }

            Glide.with(holder.itemView.getContext()).load(groceryList[position].imgSrc)
                .into(holder.imgGrocery);
        }
    }

    override fun getItemCount(): Int {
        return groceryList.size
    }


    public interface AddToCartClickListner {
        fun onclick(position: Int, gorceryItem: GroceryModel)
    }


}



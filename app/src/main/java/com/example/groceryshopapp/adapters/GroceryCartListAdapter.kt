package com.example.groceryshopapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groceryshopapp.activity.DisplayActivity
import com.example.groceryshopapp.databinding.ItemGroceryCartListBinding
import com.example.groceryshopapp.databinding.ItemGroceryListBinding
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.utils.Constants

class GroceryCartListAdapter(var groceryList: List<GroceryModel>) :
    RecyclerView.Adapter<GroceryCartListAdapter.ViewHolder>() {

    private var addToCartClickListner: RemoveFromCartClickListner? = null
    private var plusButtonClickListner:PlusButtonClickListner?=null
    private  var minusButtonClickListner:MinusButtonClickListner?=null

    fun setRemoveFromCartClickListner(clickListner: RemoveFromCartClickListner) {
        this.addToCartClickListner = clickListner
    }
    fun setPlusButtonClickListner(plusClickLisnter:PlusButtonClickListner)
    {
        this.plusButtonClickListner=plusClickLisnter
    }
    fun setMinusButtonClickListner(minusClickLisnter:MinusButtonClickListner)
    {
        this.minusButtonClickListner=minusClickLisnter
    }

    class ViewHolder(binding: ItemGroceryCartListBinding) : RecyclerView.ViewHolder(binding?.root) {

        var txtGroceryName = binding.txtGroceryName
        var imgGrocery = binding.imgGrocery
        var txtGroceryPrice = binding.txtGroceryPrice
        var txtSubtotal=binding.txtSubtotalPrice
       // var txtGroceryWeight = binding.txtGroceryWeight
        var btnAddToCart = binding.btnAddToCart
        var txtOrderedCount=binding.txtOrderedCount
        var btnPlus = binding.btnPlus
        var btnMinus = binding.btnMinus

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemGroceryCartListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            holder.txtGroceryName.text = "${groceryList[position].name} ${groceryList[position].weight}"
            //holder.txtGroceryWeight.text = groceryList[position].weight
            holder.txtGroceryPrice.text = "€ ${groceryList[position].price}"
            holder.txtOrderedCount.text=groceryList[position].orderedCount.toString()

            holder.txtSubtotal.text="€"+(groceryList[position].price*groceryList[position].orderedCount).toString()


           // holder.btnAddToCart.setText(Constants.REMOVE_FROM_CART)
            holder.btnAddToCart.setOnClickListener(View.OnClickListener {
                addToCartClickListner?.onclick(position)
            })


            holder.btnPlus.setOnClickListener {
                   plusButtonClickListner?.onclickPlusClick(position,groceryList[position])
            }
            holder.btnMinus.setOnClickListener {
                 minusButtonClickListner?.onclickMinusClick(position,groceryList[position])
            }


            Glide.with(holder.itemView.getContext()).load(groceryList[position].imgSrc)
                .into(holder.imgGrocery);

        }
    }

    override fun getItemCount(): Int {
        return groceryList.size
    }


    public interface RemoveFromCartClickListner {
        fun onclick(position: Int)
    }
    public interface PlusButtonClickListner {
        fun onclickPlusClick(position: Int, gorceryItem: GroceryModel)
    }
    public interface MinusButtonClickListner {
        fun onclickMinusClick(position: Int, gorceryItem: GroceryModel)
    }


}



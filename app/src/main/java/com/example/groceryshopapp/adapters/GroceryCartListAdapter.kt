package com.example.groceryshopapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groceryshopapp.databinding.ItemGroceryCartListBinding
import com.example.groceryshopapp.models.GroceryModel

class GroceryCartListAdapter(var groceryList: List<GroceryModel>) :
    RecyclerView.Adapter<GroceryCartListAdapter.ViewHolder>() {

    private var addToCartClickListener: RemoveFromCartClickListener? = null
    private var plusButtonClickListener: PlusButtonClickListener? = null
    private var minusButtonClickListener: MinusButtonClickListener? = null

    fun setRemoveFromCartClickListener(clickListener: RemoveFromCartClickListener) {
        this.addToCartClickListener = clickListener
    }

    fun setPlusButtonClickListener(plusClickListener: PlusButtonClickListener) {
        this.plusButtonClickListener = plusClickListener
    }

    fun setMinusButtonClickListener(minusClickLisnter: MinusButtonClickListener) {
        this.minusButtonClickListener = minusClickLisnter
    }

    class ViewHolder(binding: ItemGroceryCartListBinding) : RecyclerView.ViewHolder(binding?.root) {

        var txtGroceryName = binding.txtGroceryName
        var imgGrocery = binding.imgGrocery
        var txtGroceryPrice = binding.txtGroceryPrice
        var txtSubtotal = binding.txtSubtotalPrice
        var btnAddToCart = binding.btnAddToCart
        var txtOrderedCount = binding.txtOrderedCount
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
            holder.txtGroceryName.text =
                "${groceryList[position].name} ${groceryList[position].weight}"
            holder.txtGroceryPrice.text = "€ ${groceryList[position].price}"
            holder.txtOrderedCount.text = groceryList[position].orderedCount.toString()
            holder.txtSubtotal.text =
                "€" + (groceryList[position].price * groceryList[position].orderedCount).toString()


            holder.btnAddToCart.setOnClickListener(View.OnClickListener {
                addToCartClickListener?.onclick(position)
            })


            holder.btnPlus.setOnClickListener {
                plusButtonClickListener?.onclickPlusClick(position, groceryList[position])
            }
            holder.btnMinus.setOnClickListener {
                minusButtonClickListener?.onclickMinusClick(position, groceryList[position])
            }


            Glide.with(holder.itemView.getContext()).load(groceryList[position].imgSrc)
                .into(holder.imgGrocery);

        }
    }

    override fun getItemCount(): Int {
        return groceryList.size
    }


    public interface RemoveFromCartClickListener {
        fun onclick(position: Int)
    }

    public interface PlusButtonClickListener {
        fun onclickPlusClick(position: Int, groceryItem: GroceryModel)
    }

    public interface MinusButtonClickListener {
        fun onclickMinusClick(position: Int, groceryItem: GroceryModel)
    }


}



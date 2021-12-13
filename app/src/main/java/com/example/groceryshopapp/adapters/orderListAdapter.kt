package com.example.groceryshopapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.groceryshopapp.activity.DisplayActivity
import com.example.groceryshopapp.databinding.ItemGroceryListBinding
import com.example.groceryshopapp.databinding.ItemOrderListBinding
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.models.OrderModel
import com.example.groceryshopapp.utils.Constants

class orderListAdapter(var orderList: List<OrderModel>) :
    RecyclerView.Adapter<orderListAdapter.ViewHolder>() {

    private var viewCartClickListner: ViewCartClickListner? = null

    fun setViewCartClickListner(clickListner: ViewCartClickListner) {
        this.viewCartClickListner = clickListner
    }

    class ViewHolder(binding: ItemOrderListBinding) : RecyclerView.ViewHolder(binding?.root) {

        var txtOrderDate = binding.txtOderDate
        var btnView = binding.btnView


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemOrderListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            holder.txtOrderDate.text = orderList[position].orderlist.size.toString()

            holder.btnView.setOnClickListener {
                viewCartClickListner?.onViewCartclick(position, orderList[position])

            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }


    public interface ViewCartClickListner {
        fun onViewCartclick(position: Int, orderItem: OrderModel)
    }


}



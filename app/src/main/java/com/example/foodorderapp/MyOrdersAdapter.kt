package com.example.foodorderapp

import FoodItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(
    private val context: Context,
    private val orderList: ArrayList<FoodItem>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
        val foodName: TextView = itemView.findViewById(R.id.foodName)
        val foodDesc: TextView = itemView.findViewById(R.id.txtFoodDescDetail)
        val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // make sure your layout file is named correctly (either "item_food.xml" or "food_item.xml")
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = orderList[position]
        holder.foodName.text = item.name
        holder.foodDesc.text = item.description   // ✅ fixed
        holder.foodPrice.text = "₹${item.price}"
        holder.foodImage.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = orderList.size
}

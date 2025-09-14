package com.example.foodorderapp

import FoodItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val context: Context,
    private val cartList: List<FoodItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartImage: ImageView = itemView.findViewById(R.id.cartImage)
        val cartName: TextView = itemView.findViewById(R.id.cartName)
        val cartPrice: TextView = itemView.findViewById(R.id.cartPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartList[position]

        holder.cartName.text = currentItem.name
        holder.cartPrice.text = "₹${currentItem.price}"
        holder.cartImage.setImageResource(currentItem.imageResId)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}

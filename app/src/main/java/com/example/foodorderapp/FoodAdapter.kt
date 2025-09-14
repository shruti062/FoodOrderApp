package com.example.foodorderapp

import FoodItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private val context: Context,
    private val foodList: List<FoodItem>
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
        val foodName: TextView = itemView.findViewById(R.id.foodName)
        val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
        val btnAddToCart: Button = itemView.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = foodList[position]

        holder.foodName.text = currentItem.name
        holder.foodPrice.text = currentItem.price.toString()

        holder.foodImage.setImageResource(currentItem.imageResId)

        // ✅ Add to Cart button click
        holder.btnAddToCart.setOnClickListener {
            CartManager.addToCart(currentItem)
            Toast.makeText(context, "${currentItem.name} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = foodList.size
}

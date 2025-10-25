package com.example.foodorderapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.FoodItem

class FoodAdapter(
    private val context: Context,
    private val foodList: MutableList<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.foodName)
        val desc = itemView.findViewById<TextView>(R.id.foodDescription)
        val price = itemView.findViewById<TextView>(R.id.foodPrice)
        val image = itemView.findViewById<ImageView>(R.id.foodImg)
        val btnAdd = itemView.findViewById<Button>(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.name.text = food.name
        holder.desc.text = food.description
        holder.price.text = "₹${food.price}"
        Glide.with(context).load(food.imageUrl).into(holder.image)

        holder.itemView.setOnClickListener { onItemClick(food) }
        holder.btnAdd.setOnClickListener { CartManager.addToCart(context, food)
            Toast.makeText(holder.itemView.context, "${food.name} added to cart", Toast.LENGTH_SHORT).show()

            // Navigate directly to CartActivity
            val intent = Intent(holder.itemView.context, CartActivity::class.java)
            holder.itemView.context.startActivity(intent)}
    }

    override fun getItemCount(): Int = foodList.size

    // ✅ Fix for updateList
    fun updateList(newList: List<FoodItem>) {
        foodList.clear()
        foodList.addAll(newList)
        notifyDataSetChanged()
    }
}

package com.example.foodorderapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private val context: Context,
    private var items: MutableList<FoodItem>,
    private val onCartChanged: (() -> Unit)? = null
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartImage: ImageView = itemView.findViewById(R.id.cartImage)
        val cartName: TextView = itemView.findViewById(R.id.cartName)
        val cartPrice: TextView = itemView.findViewById(R.id.cartPrice)
        val btnRemove: Button = itemView.findViewById(R.id.cartRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.cartName.text = item.name
        holder.cartPrice.text = "₹${"%.2f".format(item.price)}"
        Glide.with(context).load(item.imageUrl).into(holder.cartImage)

        holder.btnRemove.setOnClickListener {
            CartManager.removeFromCart(context, item)
            items.removeAt(position)
            notifyItemRemoved(position)
            onCartChanged?.invoke()
            Toast.makeText(context, "${item.name} removed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newList: List<FoodItem>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun getItems(): List<FoodItem> = items
}

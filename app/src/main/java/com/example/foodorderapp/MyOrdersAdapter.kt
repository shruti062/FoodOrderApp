package com.example.foodorderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyOrdersAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<MyOrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdText: TextView = itemView.findViewById(R.id.textOrderId)
        val addressText: TextView = itemView.findViewById(R.id.textOrderAddress)
        val totalText: TextView = itemView.findViewById(R.id.textOrderTotal)
        val statusText: TextView = itemView.findViewById(R.id.textOrderStatus)
        val itemsText: TextView = itemView.findViewById(R.id.textOrderItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.orderIdText.text = "🆔 Order ID: ${order.orderId.ifEmpty { "N/A" }}"
        holder.addressText.text = "📍 Address: ${order.address}"
        holder.totalText.text = "💰 Total: ₹${String.format("%.2f", order.totalPrice)}"
        holder.statusText.text = "📦 Status: ${order.status}"

        // Build item details (like "• Pizza - ₹299")
        val itemsString = if (order.items.isNotEmpty()) {
            order.items.joinToString("\n") { item ->
                val name = item["name"]?.toString() ?: "Unknown"
                val price = (item["price"] as? Number)?.toDouble() ?: 0.0
                "• $name - ₹${String.format("%.2f", price)}"
            }
        } else {
            "No items found"
        }

        holder.itemsText.text = itemsString
    }

    override fun getItemCount(): Int = orders.size
}

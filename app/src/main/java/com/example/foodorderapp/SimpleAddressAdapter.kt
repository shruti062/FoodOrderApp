package com.example.foodorderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleAddressAdapter(private val addresses: List<Address>) :
    RecyclerView.Adapter<SimpleAddressAdapter.AddressViewHolder>() {

    class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAddress: TextView = itemView.findViewById(R.id.txtAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addresses[position]
        holder.txtAddress.text =
            "${address.name} (${address.phone})\n${address.houseNo}, ${address.city}, ${address.state} - ${address.pinCode}"
    }

    override fun getItemCount(): Int = addresses.size
}

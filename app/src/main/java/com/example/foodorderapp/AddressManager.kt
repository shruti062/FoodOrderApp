package com.example.foodorderapp

import android.content.Context

object AddressManager {
    private const val PREF_NAME = "address_prefs"
    private const val KEY_ADDRESSES = "addresses"

    fun saveAddress(context: Context, address: Address) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = getAddresses(context).toMutableList()
        existing.add(address)
        val serialized = existing.joinToString(";") {
            listOf(it.name, it.phone, it.houseNo, it.city, it.state, it.pinCode).joinToString("|")
        }
        prefs.edit().putString(KEY_ADDRESSES, serialized).apply()
    }

    fun getAddresses(context: Context): List<Address> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val serialized = prefs.getString(KEY_ADDRESSES, null) ?: return emptyList()
        return serialized.split(";").mapNotNull { entry ->
            val parts = entry.split("|")
            if (parts.size == 6) {
                Address(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5])
            } else null
        }
    }

    fun getDefaultAddress(context: Context): Address? {
        return getAddresses(context).firstOrNull()
    }
}

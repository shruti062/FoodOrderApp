package com.example.foodorderapp

import android.os.Parcel
import android.os.Parcelable

data class FoodItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0, // ✅ MUST be Double, not String
    val imageUrl: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeString(imageUrl)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<FoodItem> {
        override fun createFromParcel(parcel: Parcel): FoodItem = FoodItem(parcel)
        override fun newArray(size: Int): Array<FoodItem?> = arrayOfNulls(size)
    }
}

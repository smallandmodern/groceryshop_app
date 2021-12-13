package com.example.groceryshopapp.models

import android.os.Parcel
import android.os.Parcelable

data class GroceryModel(
    var catId: String = "",
    var id: String = "",
    var name: String = "",
    var count: Int = 0,
    var imgSrc: String = "",
    var weight: String = "",
    var price: Double = 0.0,
    var orderedCount: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(catId)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(count)
        parcel.writeString(imgSrc)
        parcel.writeString(weight)
        parcel.writeDouble(price)
        parcel.writeInt(orderedCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroceryModel> {
        override fun createFromParcel(parcel: Parcel): GroceryModel {
            return GroceryModel(parcel)
        }

        override fun newArray(size: Int): Array<GroceryModel?> {
            return arrayOfNulls(size)
        }
    }
}
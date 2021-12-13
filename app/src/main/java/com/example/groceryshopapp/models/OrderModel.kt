package com.example.groceryshopapp.models

import android.os.Parcel
import android.os.Parcelable

data class OrderModel(

    var orderlist: ArrayList<GroceryModel> = ArrayList<GroceryModel>(), var documentID: String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readArrayList(null) as ArrayList<GroceryModel>,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(documentID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }
}


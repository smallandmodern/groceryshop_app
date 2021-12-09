package com.example.groceryshopapp.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import java.io.Serializable


data class UserModel ( var id:String ="", var email:String ="",
                       var name: String = "",
     var phone:String =""):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)  {
        parcel.writeString(id)
        parcel.writeString(email)
        parcel.writeString(name)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }

}
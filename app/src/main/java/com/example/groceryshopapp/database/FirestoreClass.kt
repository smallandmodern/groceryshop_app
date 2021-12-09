package com.example.groceryshopapp.database

import com.example.groceryshopapp.activity.DisplayActivity
import com.example.groceryshopapp.activity.MyCartActivity
import com.example.groceryshopapp.activity.SignInActivity
import com.example.groceryshopapp.activity.SignUpActivity
import com.example.groceryshopapp.models.GroceryModel
import com.example.groceryshopapp.models.UserModel
import com.example.groceryshopapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    var firebaseFirestore = FirebaseFirestore.getInstance()

    fun regisrterUser(activity: SignUpActivity, userInfo: UserModel) {
        firebaseFirestore.collection(Constants.USERS).document(getCurrentUserId()).set(
            userInfo,
            SetOptions.merge()
        ).addOnCompleteListener {
            activity.onUserRegistrationCompleteLisner(it)

        }
    }


    fun signInUser(activity: SignInActivity, email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                activity.onSignInCompleteListner(it)
            }
    }
    fun getCateryList(activity: DisplayActivity) {
        firebaseFirestore.collection(Constants.CAT_LIST).get().addOnCompleteListener {

            activity.onGetCatListOnSuccessListner(it)
        }
    }

    fun getGroceryList(activity: DisplayActivity) {
        firebaseFirestore.collection(Constants.GROCERY_LIST).get().addOnCompleteListener {

            activity.onGetGroceryListOnSuccessListner(it)
        }
    }

    fun updateGroceryCountList(activity: MyCartActivity,modelList:ArrayList<GroceryModel>) {


        modelList.forEach { model ->

            val assignedToHashMap = HashMap<String, Any>()
            assignedToHashMap[Constants.COUNT] = model.count

            firebaseFirestore.collection(Constants.GROCERY_LIST).document(model.id)
                .update(assignedToHashMap).addOnCompleteListener {



                }

        }

    }
    fun getCurrentUserId(): String {

        var currentUser=FirebaseAuth.getInstance().currentUser
        var currentUserID=""

        if(currentUser!=null)
        {
            currentUserID=currentUser.uid
        }
        return  currentUserID
    }


}
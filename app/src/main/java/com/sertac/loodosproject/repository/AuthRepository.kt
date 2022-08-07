package com.sertac.loodosproject.repository

import com.google.firebase.auth.FirebaseAuth
import com.sertac.loodosproject.model.User
import com.sertac.loodosproject.util.CustomSharedPreferences
import com.sertac.loodosproject.util.Resource
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences
){
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        firebaseAuth.signOut()
//        firebaseAuth.currentUser?.let {
//            println("AuthRepository-CurrentUser --> ${it.email}")
//        }
    }

    suspend fun login(user: User, myCallback: (result: Resource<Boolean>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(user.email,user.password).addOnCompleteListener {
            if (it.isSuccessful){
                myCallback.invoke(Resource.success(true))
                customSharedPreferences.saveEmail(user.email)
            }else {
                it.exception?.let { exception ->
                    exception.message?.let { message ->
                        myCallback.invoke(Resource.error("No such user was found", null))
                    } ?: myCallback.invoke(Resource.error("Error", null))
                } ?: myCallback.invoke(Resource.error("Error", null))
            }
        }
    }

    suspend fun register(user:User, myCallback: (result: Resource<Boolean>) -> Unit){
        firebaseAuth.createUserWithEmailAndPassword(user.email,user.password).addOnCompleteListener {
            if (it.isSuccessful){
                myCallback.invoke(Resource.success(true))
            }else {
                it.exception?.let { exception ->
                    exception.message?.let { message ->
                        myCallback.invoke(Resource.error(message, null))
                    } ?: myCallback.invoke(Resource.error("Error", null))
                } ?: myCallback.invoke(Resource.error("Error", null))
            }
        }
    }

    fun logOut(){
        firebaseAuth.signOut()
    }
}
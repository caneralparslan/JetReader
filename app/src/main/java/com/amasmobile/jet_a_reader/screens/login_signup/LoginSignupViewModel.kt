package com.amasmobile.jet_a_reader.screens.login_signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.models.MUser
import com.amasmobile.jet_a_reader.navigation.ReaderScreens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginSignupViewModel: ViewModel(){

    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun loginWithEmailAndPassword(
        email: String,
        password: String,
        navController: NavController,
        context: Context
    ) {
        _loading.value = true

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loading.value = false // Always stop loading here regardless of success/failure

                if (task.isSuccessful) {
                    Log.d("User Login Successful", "loginWithEmailAndPassword: ${task.result}")
                    navController.navigate(ReaderScreens.HomeScreen.name)
                } else {
                    Log.d("Login Unsuccessful", "loginWithEmailAndPassword: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                _loading.value = false
                Toast.makeText(context, "Email or Password is Incorrect!", Toast.LENGTH_SHORT).show()
                Log.e("Login Error", "Exception: ${exception.localizedMessage}")
            }
    }

    fun createUserWithEmailAndPassword(email: String,
                                       password: String,
                                       navController: NavController,
                                       context: Context) {
        _loading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loading.value = false

                if (task.isSuccessful) {
                    val userName = task.result.user?.email?.split("@")?.get(0)
                    createUserToFirestoreDb(userName!!)

                    Log.d("User Create Successful", "createUserWithEmailAndPassword: ${task.result}")
                    navController.navigate(ReaderScreens.HomeScreen.name)
                }
                else {
                    Log.d("User Create Unsuccessful", "createUserWithEmailAndPassword: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                _loading.value = false
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.e("Signup Error", "Exception: ${exception.localizedMessage}")
            }
    }


    private fun createUserToFirestoreDb(
        userName: String
    ){
        val userId = auth.currentUser?.uid

        val user = MUser(
            id = null,
            userId = userId?: "Sikici",
            userName = userName,
            avatarUrl = "",
            quote = "Selamın aleyküm gardeş",
            profession = "Android Developer"
        ).toMap()

        FirebaseFirestore.getInstance().collection("users").add(user)

    }

}


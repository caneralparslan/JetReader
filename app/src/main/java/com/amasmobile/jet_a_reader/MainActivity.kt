package com.amasmobile.jet_a_reader

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.amasmobile.jet_a_reader.navigation.ReaderNavigation
import com.amasmobile.jet_a_reader.ui.theme.JetAReaderTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.initialize
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetAReaderTheme {
/*

                val db = FirebaseFirestore.getInstance()
                val user: MutableMap<String, Any> = HashMap()
                user["firstName"] = "Caner"
                user["lastName"] = "Alparslan"

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Log.d("User Add Success", "Successfully created user")
                    }
                    .addOnFailureListener {
                        Log.d("User Add Failure", "User Couldn't be added!")
                    }
*/

                ReaderNavigation()

            }
        }
    }
}

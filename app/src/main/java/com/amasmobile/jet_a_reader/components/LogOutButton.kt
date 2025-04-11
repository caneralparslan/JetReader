package com.amasmobile.jet_a_reader.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LogOutButton(navController: NavController){
    IconButton(
        onClick = {
            FirebaseAuth.getInstance().signOut()
            navController.navigate(ReaderScreens.LoginScreen.name){
                popUpTo(0) { inclusive = true }
            }
        }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            tint = Color(0xFF87C466),
            imageVector = Icons.AutoMirrored.Default.ExitToApp,
            contentDescription = "Exit Button")
    }
}
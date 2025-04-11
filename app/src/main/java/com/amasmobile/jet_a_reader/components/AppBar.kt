package com.amasmobile.jet_a_reader.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavController,
    title: String){

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(title,
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        },
        navigationIcon = {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back Arrow",
                tint = Color.Red,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                })
        }
    )
}
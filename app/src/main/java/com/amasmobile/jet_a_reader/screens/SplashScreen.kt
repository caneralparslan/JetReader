package com.amasmobile.jet_a_reader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = {
                    OvershootInterpolator(5f)
                        .getInterpolation(it)
                })
        )
        delay(1000).apply {
            loginControl(navController)
        }
    })

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        CircleContent(scale)

    }
}


fun loginControl(navController: NavController){

    //if user logged in navigate to MainScreen else LoginScreen

    if(FirebaseAuth.getInstance().currentUser == null){
        navController.navigate(ReaderScreens.LoginScreen.name){
            popUpTo(0) { inclusive = true }
        }
    }
    else{
        navController.navigate(ReaderScreens.HomeScreen.name){
            popUpTo(0) { inclusive = true }
        }
    }

}

@Composable
fun CircleContent(scale: Animatable<Float, AnimationVector1D>){
    Surface(
        modifier = Modifier.size(300.dp).padding(10.dp).scale(scale.value),
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("A. Reader",
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Red.copy(alpha = 0.8f)
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text("\"Read. Change. Yourself\"",
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.LightGray.copy(alpha = 0.8f)
                ))
        }
    }
}
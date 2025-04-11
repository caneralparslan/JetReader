package com.amasmobile.jet_a_reader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amasmobile.jet_a_reader.screens.BookDetailsScreen
import com.amasmobile.jet_a_reader.screens.BookUpdateScreen
import com.amasmobile.jet_a_reader.screens.login_signup.LoginScreen
import com.amasmobile.jet_a_reader.screens.MainScreen
import com.amasmobile.jet_a_reader.screens.search.SearchScreen
import com.amasmobile.jet_a_reader.screens.login_signup.SignUpScreen
import com.amasmobile.jet_a_reader.screens.SplashScreen
import com.amasmobile.jet_a_reader.screens.StatsScreen

@Composable
fun ReaderNavigation(){

    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(route = ReaderScreens.SplashScreen.name){
            SplashScreen(navController)
        }

        composable(
            route = ReaderScreens.LoginScreen.name
        ){
            LoginScreen(navController)
        }

        composable(
            route = ReaderScreens.SignUpScreen.name
        ){
            SignUpScreen(navController)
        }

        composable(
            route = ReaderScreens.MainScreen.name
        ){
            MainScreen(navController)
        }

        composable("${ReaderScreens.BookDetailsScreen.name}/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            BookDetailsScreen(
                navController = navController,
                bookId = bookId ?: "")
        }

        composable(
            route = ReaderScreens.StatsScreen.name
        ){
            StatsScreen(navController)
        }

        composable(
            route = ReaderScreens.SearchScreen.name
        ){
            SearchScreen(navController)
        }

        composable(
            route = ReaderScreens.BookUpdateScreen.name
        ){
            BookUpdateScreen(navController)
        }

    }
}
package com.amasmobile.jet_a_reader.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.R
import com.amasmobile.jet_a_reader.components.BookCard
import com.amasmobile.jet_a_reader.components.LogOutButton
import com.amasmobile.jet_a_reader.navigation.ReaderScreens
import com.amasmobile.jet_a_reader.utils.getBooks
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            FABContent{
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        padding ->
        MainContent(padding, navController)

    }
}

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = onTap,
        shape = CircleShape,
        containerColor = Color(0xFF83BCEC)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Book")
    }
}

@Composable
fun MainContent(padding: PaddingValues,
                navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding(),
                bottom = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ){

        TopBar(navController)
        TitleRow(navController)
        ReadingListRow(navController)
        Text("Reading List",
            modifier = Modifier.padding(start = 20.dp))
        ReadingListRow(navController)

    }
}
@Composable
fun ReadingListRow(navController: NavController){
    val books = getBooks()

    LazyRow{
        items(items = books){
            book ->
            Row {
                BookCard(navController = navController,
                   items = book)
            }
        }
    }
}



@Composable
fun TitleRow(navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text("Your Reading\nActivity Right Now")

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val userName = FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0) ?: "User"

            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(30.dp).clickable {
                    navController.navigate(ReaderScreens.StatsScreen.name)
                } )

            Text(userName,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF255377),
                    fontWeight = FontWeight.Medium
                ),
                overflow = TextOverflow.Ellipsis)
        }

    }
}


@Composable
fun TopBar(navController: NavController){
    Row (
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.app_icon),
                contentDescription = "App Icon")
            Text("  A. Reader",
                style = TextStyle(
                    color = Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        LogOutButton(navController)
    }
}

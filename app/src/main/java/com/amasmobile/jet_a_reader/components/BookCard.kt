package com.amasmobile.jet_a_reader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.amasmobile.jet_a_reader.models.Items
import com.amasmobile.jet_a_reader.navigation.ReaderScreens

@Composable
fun BookCard(items: Items,
             navController: NavController = rememberNavController()){

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .height(250.dp)
            .width(200.dp)
            .padding(start = 20.dp, end = 10.dp)
            .clickable {
                navController.navigate(ReaderScreens.BookDetailsScreen.name + "/${items.id}")
            }

    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 5.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            //Book Cover and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(items.volumeInfo.imageLinks.thumbnail),
                    contentDescription = "Book Cover",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(120.dp)
                        .width(110.dp)
                        .background(Color.LightGray)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Fav Button")

                    Spacer(Modifier.height(8.dp))
                    Surface(
                        shadowElevation = 5.dp,
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "Star Icon")
                            Text("0.0")
                        }
                    }
                }
            }

            Spacer(Modifier.height(15.dp))

            // Title
            Text(items.volumeInfo.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(15.dp))

            // Author
            Text("${items.volumeInfo.authors?.toString()}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )


            Spacer(Modifier.height(15.dp))

            // Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    shape = RoundedCornerShape(
                        bottomEnd = 20.dp,
                        topStart = 20.dp
                    ),
                    color = Color(0xFF83BCEC),
                    modifier = Modifier
                        .height(30.dp)
                        .width(80.dp)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text("Reading",
                            style = TextStyle(
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            }

        }
    }
}
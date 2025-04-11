package com.amasmobile.jet_a_reader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.amasmobile.jet_a_reader.models.Items

@Composable
fun BookTile(items: Items){
    Surface(
        shadowElevation = 2.dp,
        shape = RectangleShape,
        color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
            .clickable {

            }
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                modifier = Modifier.width(110.dp).height(120.dp),
                painter = rememberAsyncImagePainter(items.volumeInfo.imageLinks.thumbnail), contentDescription = "Book Cover")

            Spacer(Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(items.volumeInfo.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text("Author: ${items.volumeInfo.authors?.get(0)}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )
                )

                Text("Date: ${items.volumeInfo.publishedDate}",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )

                Text("[${items.volumeInfo.categories?.get(0)}]")
            }
        }

    }
}
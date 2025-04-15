package com.amasmobile.jet_a_reader.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SaveCancelButtons(text: String, onClick: () -> Unit ){


    Surface(
        shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
        color = Color(0xFF51AFB4),
        modifier = Modifier
            .height(35.dp).width(75.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center
        ){
            Text(text,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }

}
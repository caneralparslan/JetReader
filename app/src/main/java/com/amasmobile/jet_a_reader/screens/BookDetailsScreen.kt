package com.amasmobile.jet_a_reader.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.utils.getBookById
import com.amasmobile.jet_a_reader.utils.getBooks

@Composable
fun BookDetailsScreen(navController: NavController,
                      bookId: String) {

    val book = getBookById(bookId = bookId,
                            items = getBooks())
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Book Details Screen")
        Text("Book Title -> ${book.volumeInfo.title}")
        Text("Book Author -> ${book.volumeInfo.authors?.get(0)}")
        Text("Book Notes-> ${book.volumeInfo.subtitle}")
    }
}
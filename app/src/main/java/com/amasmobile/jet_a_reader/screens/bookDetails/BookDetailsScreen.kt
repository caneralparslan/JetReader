package com.amasmobile.jet_a_reader.screens.bookDetails

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.amasmobile.jet_a_reader.components.AppBar
import com.amasmobile.jet_a_reader.components.BlueButton
import com.amasmobile.jet_a_reader.models.Items
import com.amasmobile.jet_a_reader.models.MBook
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BookDetailsScreen(navController: NavController,
                      bookId: String,
                      bookDetailsViewModel: BookDetailsViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ){
        AppBar(navController, "Book Details")
        BookDetailsContent(navController, bookId, bookDetailsViewModel)
    }
}

@Composable
fun BookDetailsContent(navController: NavController,
                       bookId: String,
                       bookDetailsViewModel: BookDetailsViewModel) {


    LaunchedEffect(bookId) {
        bookDetailsViewModel.getBookDetails(bookId)
    }

    val bookDetails = bookDetailsViewModel.bookDetails.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        when {
            bookDetails.loading == true ->
                CircularProgressIndicator()

            bookDetails.data != null ->
            {
                Log.d("book details to string", "BookDetailsScreen: ${bookDetails.data.toString()}")
                Details(bookDetails.data!!, navController, bookDetailsViewModel)
            }

            bookDetails.e != null ->
            {
                Log.d("Book Details Error", "BookDetailsScreen: ${bookDetails.e}")
                Text("Something went wrong!",
                    color = Color.Red
                )
            }

            else ->
                Text("No Details Found!")
        }
    }
}

@Composable
fun Details(book: Items, navController: NavController, bookDetailsViewModel: BookDetailsViewModel){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = CircleShape,
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            modifier = Modifier.size(150.dp)
        ) {
            Image(painter = rememberAsyncImagePainter(book.volumeInfo.imageLinks.thumbnail),
                contentDescription = "Book Cover")
        }

        Spacer(Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(book.volumeInfo.title,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text("Authors: ${book.volumeInfo.authors?.get(0)}", textAlign = TextAlign.Center)
            Text("Page Count: ${book.volumeInfo.pageCount}")
            Text("Categories: ${book.volumeInfo.categories.toString()}",
                textAlign = TextAlign.Center,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis)
            Text("Published: ${book.volumeInfo.publishedDate}")
        }

        val description = book.volumeInfo.description?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        }

        Surface (
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp),
            shape = RectangleShape,
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ){
            LazyColumn {
                item {
                    Text(description?: "",
                        modifier = Modifier.padding(10.dp))
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        val context = LocalContext.current
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val isSavingOnProgress = remember {
                mutableStateOf(false)
            }

            BlueButton("Save") {
                isSavingOnProgress.value = true

                val bookToSave = MBook(
                    title = book.volumeInfo.title,
                    authors = book.volumeInfo.authors.toString(),
                    description = book.volumeInfo.description,
                    categories = book.volumeInfo.categories.toString(),
                    notes = "",
                    photoUrl = book.volumeInfo.imageLinks.thumbnail,
                    publishedDate = book.volumeInfo.publishedDate,
                    pageCount = book.volumeInfo.pageCount.toString(),
                    rating = 0.0,
                    googleBookId = book.id,
                    userId = FirebaseAuth.getInstance().currentUser?.uid
                )

                bookDetailsViewModel.saveBookToFirebase(bookToSave){
                    isSuccess, message ->
                    if(isSuccess){
                        Toast.makeText(context, "Book Saved Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if(message == null){
                            Toast.makeText(context, "Book Couldn't Saved", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            Log.d("Firebase Save Error", "Firebase Save Error: $message")
                        }
                    }
                    isSavingOnProgress.value = false
                    navController.popBackStack()
                }

            }


            if(isSavingOnProgress.value){
                CircularProgressIndicator(
                    modifier = Modifier.size(35.dp)
                )
            }
            else{
                Spacer(Modifier.width(35.dp))
            }


            BlueButton("Cancel") {
                navController.popBackStack()
            }
        }
    }
}



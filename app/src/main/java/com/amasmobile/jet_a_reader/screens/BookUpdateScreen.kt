package com.amasmobile.jet_a_reader.screens

import android.media.Rating
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.amasmobile.jet_a_reader.components.AppBar
import com.amasmobile.jet_a_reader.components.BlueButton
import com.amasmobile.jet_a_reader.components.RatingBar
import com.amasmobile.jet_a_reader.models.MBook
import com.amasmobile.jet_a_reader.navigation.ReaderScreens
import com.amasmobile.jet_a_reader.screens.bookDetails.BookDetailsContent
import com.amasmobile.jet_a_reader.screens.bookDetails.BookDetailsViewModel
import com.amasmobile.jet_a_reader.screens.home.FABContent
import com.amasmobile.jet_a_reader.screens.home.HomeViewModel
import com.amasmobile.jet_a_reader.screens.home.MainContent
import com.amasmobile.jet_a_reader.utils.bookExample
import com.amasmobile.jet_a_reader.utils.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun BookUpdateScreen(navController: NavController,
                     bookId: String,
                     viewModel: HomeViewModel = hiltViewModel()) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Top,
    ){
        AppBar(navController, "Update Book")

        UpdateBookContent(navController, bookId, viewModel)
    }
}

@Composable
fun UpdateBookContent(navController: NavController,
                  bookId: String,
                  viewModel: HomeViewModel) {


    val booksList = viewModel.booksData.collectAsState().value.data

    if(booksList.isNullOrEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }
    else{
        val book = booksList.firstOrNull { it.id == bookId }

        if (book != null) {

            val thoughtsText = remember {
                mutableStateOf(book.notes ?: "")
            }
            val bookRating = remember { mutableIntStateOf(0) }

            val isStartedReading = remember { mutableStateOf(false) }
            val isFinishedReading = remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BookDetailsCard(book)

                Spacer(Modifier.height(50.dp))
                ThoughtsTextField(thoughtsText)

                Spacer(Modifier.height(50.dp))
                TextButtonsRow(book, isStartedReading, isFinishedReading)

                Spacer(Modifier.height(20.dp))
                Text("Rating", style = TextStyle(fontSize = 18.sp))

                Spacer(Modifier.height(10.dp))
                Rating(book, bookRating)

                Spacer(Modifier.height(40.dp))
                UpdateDeleteButtonsRow(
                    thoughtsText = thoughtsText,
                    bookRating = bookRating,
                    isStartedReading = isStartedReading,
                    isFinishedReading = isFinishedReading,
                    book = book,
                    navController)
            }
        }
    }

}

@Composable
fun UpdateDeleteButtonsRow(thoughtsText: MutableState<String>,
                           bookRating: MutableIntState,
                           isStartedReading: MutableState<Boolean>,
                           isFinishedReading: MutableState<Boolean>,
                           book: MBook,
                           navController: NavController) {

    val context = LocalContext.current

    val isThoughtsChanged = thoughtsText.value != book.notes
    val isRatingChanged = bookRating.intValue != book.rating?.toInt()
    val finishedAt = if(isFinishedReading.value) Timestamp.now() else book.finishedReading
    val startedAt = if(isStartedReading.value) Timestamp.now() else book.startReading

    val isBookUpdatable = isThoughtsChanged || isRatingChanged || isStartedReading.value || isFinishedReading.value

    val bookToUpdate = hashMapOf(
        "finished_reading" to finishedAt,
        "start_reading" to startedAt,
        "rating" to bookRating.intValue,
        "notes" to thoughtsText.value
    ).toMap()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        BlueButton("Update") {
            if (isBookUpdatable){
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .update(bookToUpdate)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            Toast.makeText(context, "Changes Saved Successfully", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        else{
                            Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
                            Log.d("Update Book Error", "Update Book Error: ${task.exception}")
                        }
                    }
            }
            else{
                Toast.makeText(context, "No changes made", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(Modifier.width(50.dp))

        val openDialog = remember { mutableStateOf(false) }

        BlueButton("Delete") {
            openDialog.value = true
        }
        if (openDialog.value) DeleteBookAlertDialog(openDialog, book, navController)
    }
}

@Composable
fun DeleteBookAlertDialog(openDialog: MutableState<Boolean>,
                          book: MBook,
                          navController: NavController) {

    val context = LocalContext.current

    if(openDialog.value){
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },

            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(onClick = {
                        // Yes action
                        openDialog.value = false

                        FirebaseFirestore.getInstance()
                            .collection("books")
                            .document(book.id!!)
                            .delete()
                            .addOnCompleteListener{
                                task ->
                                if (task.isSuccessful){
                                    Toast.makeText(context, "Book Deleted!", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ReaderScreens.HomeScreen.name){
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                                else{
                                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                                }
                            }



                    }) {
                        Text("Yes")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    ElevatedButton(onClick = {
                        // Cancel
                        openDialog.value = false
                    }) {
                        Text("Cancel")
                    }
                }
            },
            title = { Text("Delete Book",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())},
            text = { Text("Are you sure to delete this book?",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())},
            containerColor = Color.LightGray
        )
    }
}

@Composable
fun Rating(book: MBook,
           bookRating: MutableIntState) {
    RatingBar(
        rating = book.rating?.toInt() ?: 0,
    ) {
        bookRating.intValue = it
    }
}


@Composable
fun TextButtonsRow(book: MBook,
                   isStartedReading: MutableState<Boolean>,
                   isFinishedReading: MutableState<Boolean> ) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 50.dp)
    ) {

        TextButton(
            onClick = {
                isStartedReading.value = true
            },
            enabled = book.startReading == null
        ) {
            if(book.startReading == null){
                if(!isStartedReading.value){
                    Text("Start Reading",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color(0xFF391273),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    )
                }
                else{
                    Text("Started Reading!",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color(0xFF391273).copy(alpha = 0.5f),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    )
                }
            }
            else{
                Text("Started on:\n${formatDate(book.startReading!!)}",
                    textAlign = TextAlign.Center)
            }
        }

        Spacer(Modifier.width(20.dp))

        TextButton(
            onClick = {
                isFinishedReading.value = true
            },
            enabled = book.finishedReading == null
        ) {
            if(book.finishedReading == null){
                if(!isFinishedReading.value){
                    Text("Mark as Read",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color(0xFF391273),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    )
                }
                else{
                    Text("Finished Reading!",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color(0xFF391273).copy(alpha = 0.5f),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp
                        )
                    )
                }
            }
            else{
                Text("Finished on:\n${formatDate(book.finishedReading!!)}",
                    textAlign = TextAlign.Center,)
            }
        }
    }
}


@Composable
fun ThoughtsTextField(thoughtsText: MutableState<String>) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 35.dp),
        value = thoughtsText.value,
        onValueChange = {
            thoughts ->
            thoughtsText.value = thoughts
        },
        label = {
            Text("Enter Your Thoughts..")
        },
        placeholder = {
            Text("No Thoughts Available :( ")
        }
    )
}

@Preview
@Composable
fun BookDetailsCard(book: MBook = bookExample) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 20.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(60.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 60.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = rememberAsyncImagePainter(book.photoUrl),
                contentDescription = "Book Cover",
                modifier = Modifier.height(200.dp))

            Spacer(Modifier.width(30.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(book.title.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(book.authors.toString())

                Text(book.publishedDate.toString())
            }


        }
    }
}

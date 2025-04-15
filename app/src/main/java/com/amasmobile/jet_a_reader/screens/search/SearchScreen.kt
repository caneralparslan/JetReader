package com.amasmobile.jet_a_reader.screens.search

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.components.AppBar
import com.amasmobile.jet_a_reader.components.BookTile

@Composable
fun SearchScreen(navController: NavController,
                 searchViewModel: SearchViewModel = hiltViewModel()) {

    Surface {
        SearchContent(navController,
            searchViewModel)
    }

}

@Composable
fun SearchContent(navController: NavController,
                  searchViewModel: SearchViewModel) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AppBar(
            navController = navController,
            title = "Search Books"
        )
        SearchTextField(searchViewModel)
        Spacer(Modifier.height(30.dp))
        BookList(searchViewModel, navController)
        Spacer(Modifier.height(20.dp))
    }

}

@Composable
fun BookList(searchViewModel: SearchViewModel,
             navController: NavController) {
    val allBooksState = searchViewModel.allBooksData.collectAsState().value
    val books = allBooksState.data?.items

    when {
        allBooksState.loading == true -> {
            CircularProgressIndicator()

        }
        !books.isNullOrEmpty() -> {
            LazyColumn {
                items(books) { book ->
                    BookTile(book, navController)
                }
            }
        }
        allBooksState.e != null -> {
            Text(
                text = "A Problem Occurred!",
                modifier = Modifier.padding(16.dp),
                color = Color.Red
            )
        }
        else -> {
            Text(text = "No books found.", modifier = Modifier.padding(16.dp))
        }
    }
}


@Composable
fun SearchTextField(searchViewModel: SearchViewModel){
    val bookSearchState = rememberSaveable() {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        value = bookSearchState.value,
        onValueChange = {
            bookSearchState.value = it
        },
        label = {
            Text("Book Title",
                style = TextStyle(
                    color = Color.Gray.copy(0.9f),
                )
            )
        },

        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions{
            if(bookSearchState.value.trim().isEmpty()) return@KeyboardActions

            keyboardController?.hide()
            searchViewModel.searchBook(bookSearchState.value)
        }
    )
}
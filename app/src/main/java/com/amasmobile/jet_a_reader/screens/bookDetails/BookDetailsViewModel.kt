package com.amasmobile.jet_a_reader.screens.bookDetails

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.amasmobile.jet_a_reader.data.DataOrException
import com.amasmobile.jet_a_reader.models.Items
import com.amasmobile.jet_a_reader.models.MBook
import com.amasmobile.jet_a_reader.repository.BookRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

    private val _bookDetails = MutableStateFlow(DataOrException<Items, Boolean, Exception>())
    val bookDetails : StateFlow<DataOrException<Items, Boolean, Exception>> = _bookDetails


    fun getBookDetails(bookId: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(bookId.isEmpty()) return@launch

            _bookDetails.value = DataOrException(null, true, Exception(""))
            _bookDetails.value = repository.getBookInfo(bookId)
        }
    }

    fun saveBookToFirebase(book: MBook, onResult: (Boolean, String?) -> Unit)
    {
        val dbCollection = FirebaseFirestore.getInstance().collection("books")

        if(book.toString().isNotEmpty()){
            dbCollection.add(book)
                .addOnSuccessListener {
                        docRef ->
                    val documentRef = docRef.id
                    dbCollection.document(documentRef)
                        .update(hashMapOf("id" to documentRef) as Map<String, Any>)
                        .addOnCompleteListener{
                                task ->
                            onResult(task.isSuccessful, null)
                        }
                }
                .addOnFailureListener {
                    onResult(false, it.localizedMessage)
                }
        }

    }
}
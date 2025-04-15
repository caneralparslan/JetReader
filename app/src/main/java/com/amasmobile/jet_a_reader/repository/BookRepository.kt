package com.amasmobile.jet_a_reader.repository

import android.util.Log
import com.amasmobile.jet_a_reader.data.DataOrException
import com.amasmobile.jet_a_reader.models.Books
import com.amasmobile.jet_a_reader.models.Items
import com.amasmobile.jet_a_reader.network.BooksApi
import javax.inject.Inject


class BookRepository @Inject constructor(private val booksApi: BooksApi){

    private var allBooksDataOrException = DataOrException<Books, Boolean, Exception>()

    suspend fun getAllBooks(query: String) : DataOrException<Books, Boolean, Exception> {
        try {
            allBooksDataOrException.loading = true
            allBooksDataOrException.data = booksApi.getAllBooks(query)
            if(allBooksDataOrException.data.toString().isNotEmpty()) allBooksDataOrException.loading = false
        }
        catch (exception: Exception){
            allBooksDataOrException = DataOrException(null, false, exception)
        }

        return allBooksDataOrException
    }




    private val itemsDataOrException = DataOrException<Items, Boolean, Exception>()

    suspend fun getBookInfo(bookId: String) : DataOrException<Items, Boolean, Exception>{

        try {
            itemsDataOrException.loading = true
            itemsDataOrException.data = booksApi.getBookInfo(bookId)
            if(itemsDataOrException.data.toString().isNotEmpty()) itemsDataOrException.loading = false
        }
        catch (exception: Exception){
            itemsDataOrException.e = exception
        }

        return itemsDataOrException
    }
}
package com.amasmobile.jet_a_reader.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amasmobile.jet_a_reader.data.DataOrException
import com.amasmobile.jet_a_reader.models.Books
import com.amasmobile.jet_a_reader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    private val _allBooksData = MutableStateFlow(DataOrException<Books, Boolean, Exception>())
    val allBooksData: StateFlow<DataOrException<Books, Boolean, Exception>> = _allBooksData

    // Track the current query to avoid handling old requests
    private var currentQuery: String? = null

    init {
        searchBook("android")
    }

    fun searchBook(query: String) {
        // If the query hasn't changed, no need to do anything
        if (query == currentQuery) return

        // Update the current query to prevent using stale data
        currentQuery = query

        viewModelScope.launch(Dispatchers.IO) {
            if (query.isEmpty()) return@launch

            _allBooksData.value = DataOrException(data = null, loading = true, e = null)
            _allBooksData.value = repository.getAllBooks(query)
        }
    }
}

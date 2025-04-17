package com.amasmobile.jet_a_reader.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amasmobile.jet_a_reader.data.DataOrException
import com.amasmobile.jet_a_reader.models.Books
import com.amasmobile.jet_a_reader.models.MBook
import com.amasmobile.jet_a_reader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: FireRepository): ViewModel() {

    private val _booksData = MutableStateFlow(DataOrException<List<MBook>, Boolean, Exception>())
    val booksData: StateFlow<DataOrException<List<MBook>, Boolean, Exception>> = _booksData

    init {
        getAllBooksFromDatabase()
    }

    fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            _booksData.value.loading = true
            val result = repository.getAllBooksFromDatabase()
            _booksData.value = result.apply { loading = false }
        }
    }

}
package com.amasmobile.jet_a_reader.repository

import com.amasmobile.jet_a_reader.data.DataOrException
import com.amasmobile.jet_a_reader.models.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FireRepository @Inject constructor(private val queryBook: Query) {

    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        return try {
            val books = queryBook.get().await().documents.mapNotNull {
                it.toObject(MBook::class.java)
            }
            DataOrException(data = books, loading = false, e = null)
        } catch (e: FirebaseFirestoreException) {
            DataOrException(data = null, loading = false, e = e)
        }
    }

}
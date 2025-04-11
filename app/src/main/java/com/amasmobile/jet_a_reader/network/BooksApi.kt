package com.amasmobile.jet_a_reader.network

import com.amasmobile.jet_a_reader.models.Books
import com.amasmobile.jet_a_reader.models.Items
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getAllBooks(
        @Query("q") query: String
    ): Books

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(
        @Path("bookId") bookId: String
    ): Items


}
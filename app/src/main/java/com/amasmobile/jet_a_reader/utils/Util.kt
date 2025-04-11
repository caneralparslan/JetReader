package com.amasmobile.jet_a_reader.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.amasmobile.jet_a_reader.models.AccessInfo
import com.amasmobile.jet_a_reader.models.Items
import com.amasmobile.jet_a_reader.models.Epub
import com.amasmobile.jet_a_reader.models.ImageLinks
import com.amasmobile.jet_a_reader.models.IndustryIdentifier
import com.amasmobile.jet_a_reader.models.ListPrice
import com.amasmobile.jet_a_reader.models.ListPriceX
import com.amasmobile.jet_a_reader.models.Offer
import com.amasmobile.jet_a_reader.models.PanelizationSummary
import com.amasmobile.jet_a_reader.models.Pdf
import com.amasmobile.jet_a_reader.models.ReadingModes
import com.amasmobile.jet_a_reader.models.RetailPrice
import com.amasmobile.jet_a_reader.models.RetailPriceX
import com.amasmobile.jet_a_reader.models.SaleInfo
import com.amasmobile.jet_a_reader.models.SearchInfo
import com.amasmobile.jet_a_reader.models.VolumeInfo

class Util {

    companion object {
        private val validDomains = listOf("gmail.com", "yahoo.com", "outlook.com", "hotmail.com")

        fun isValidEmail(email: String): Boolean {
            val parts = email.split("@")
            return parts.size == 2 && validDomains.contains(parts[1]) && parts[0].isNotEmpty()
        }

        fun isValidPassword(password: String): Boolean{
            return password.length >= 6
        }
    }

}

fun isCredentialsValid(
    context: Context,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    rePasswordState: MutableState<String>
): Boolean {
    val email = emailState.value.trim()
    val password = passwordState.value

    val isEmailValid = Util.isValidEmail(email)
    val isPasswordValid = Util.isValidPassword(password)
    val passwordsMatch = passwordState.value == rePasswordState.value

    if (!isEmailValid || !isPasswordValid || !passwordsMatch) {
        val message = when {
            !isEmailValid -> "Please enter a valid email address."
            !isPasswordValid -> "Password should be at least 6 characters."
            else -> "Passwords don't match!"
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Log.d("LoginValidation", message)

        return false
    }

    return true
}

val items = Items(
    accessInfo = AccessInfo(
        accessViewStatus = "",
        country = "",
        embeddable = true,
        epub = Epub(
            acsTokenLink = "",
            downloadLink = "",
            isAvailable = true
        ),
        pdf = Pdf(
            acsTokenLink = "",
            downloadLink = "",
            isAvailable = true
        ),
        publicDomain = false,
        quoteSharingAllowed = false,
        textToSpeechPermission = "",
        viewability = "",
        webReaderLink = ""
    ),
    eTag = "",
    id = "11",
    kind = "Roman",
    saleInfo = SaleInfo(
        buyLink = "",
        country = "TR",
        isEbook = true,
        listPrice = ListPrice(amount = 13.99, currencyCode = "USD"),
        offers = listOf(
            Offer(
                finskyOfferType = 1,
                listPrice = ListPriceX(amountInMicros = 1, currencyCode = "USD"),
                retailPrice = RetailPrice(amountInMicros = 1, currencyCode = "USD")
            )
        ),
        retailPrice = RetailPriceX(amount = 1.5, currencyCode = "USD"),
        saleability = ""
    ),
    searchInfo = SearchInfo(textSnippet = ""),
    selfLink = "",
    volumeInfo = VolumeInfo(
        allowAnonLogging = true,
        authors = listOf("Puşkin"),
        averageRating = 8,
        canonicalVolumeLink = "",
        categories = listOf("anan"),
        contentVersion = "1",
        description = "yok",
        imageLinks = ImageLinks(smallThumbnail = "", thumbnail = "http://books.google.com/books/content?id=73RpCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
        industryIdentifiers = listOf(IndustryIdentifier(identifier = "", type = "")),
        infoLink = "",
        language = "",
        maturityRating = "",
        pageCount = 5,
        panelizationSummary = PanelizationSummary(containsEpubBubbles = false, containsImageBubbles = false),
        previewLink = "",
        printType = "",
        publishedDate = "2025-01-31",
        publisher = "",
        ratingsCount = 0,
        readingModes = ReadingModes(image = false, text = false),
        subtitle = "",
        title = "Mına koydumun Kitabı"
    )
)

fun getBooks(): List<Items> {
    return listOf(items, items, items, items)
}

fun getBookById(bookId: String, items: List<Items>): Items{
    return items.filter {
        it.id == bookId }[0]
}
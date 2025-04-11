package com.amasmobile.jet_a_reader.models

data class Books(
    val items: List<Items>,
    val kind: String,
    val totalItems: Int
)
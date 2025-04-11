package com.amasmobile.jet_a_reader.models

data class Items(
    val accessInfo: AccessInfo,
    val eTag: String,
    val id: String,
    val kind: String,
    val saleInfo: SaleInfo,
    val searchInfo: SearchInfo,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)
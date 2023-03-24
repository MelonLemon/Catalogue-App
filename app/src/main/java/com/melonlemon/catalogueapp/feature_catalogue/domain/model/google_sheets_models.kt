package com.melonlemon.catalogueapp.feature_catalogue.domain.model

data class RecordObject(
    val range: String,
    val majorDimension: String,
    val values: List<List<String>>
)
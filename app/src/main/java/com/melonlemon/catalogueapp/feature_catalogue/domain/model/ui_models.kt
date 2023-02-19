package com.melonlemon.catalogueapp.feature_catalogue.domain.model

data class CardInfo(
    val id: Int,
    val title: String,
    val tags: List<String>,
    val photoId: Int
)

data class CategoryInfo(
    val id: Int,
    val name: String
)

data class ColumnInfo(
    val id: Int,
    val label: String,
    val text: String
)

data class SelectedCategoryInfo(
    val id: Int,
    val name: String,
    val isSelected: Boolean
)

data class RecordInfo(
    val id: Int,
    val name: String,
    val urlString: String,
    val columnsInfo: List<ColumnInfo>,
    val tags: List<String>
)
package com.melonlemon.catalogueapp.feature_catalogue.domain.model

data class CardInfo(
    val id: Int,
    val hostId: Int,
    val title: String,
    val tags: List<String>,
    val photoPath: String?
)

data class FilterFields(
    val searchText: String,
    val categoryId: Int
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

data class FileInfo(
    val id: Int,
    val name: String,
    val folderId: Int,
    val urlPath: String,
    val urlCoverImage: String,
    val columns: List<String>,
    val tags: List<String>
)

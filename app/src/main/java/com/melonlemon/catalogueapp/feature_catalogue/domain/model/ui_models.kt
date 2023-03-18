package com.melonlemon.catalogueapp.feature_catalogue.domain.model

data class CardInfo(
    val id: Int,
    val hostId: Int,
    val title: String,
    val tags: List<String>,
    val photoPath: String?
)

data class CategoryInfo(
    val id: Int,
    val name: String
)

data class ColumnInfo(
    val label: String,
    val text: String
)

data class RecordInfo(
    val id: Int,
    val title: String,
    val category: String,
    val coverImg: String?,
    val columnsInfo: List<ColumnInfo>
)

data class FileInfo(
    val id: Int,
    val name: String,
    val folderId: Int,
    val imageCover: String?,
    val infoForLoading: InfoForLoading,
    val columns: List<String>,
    val tags: List<String>
)

data class InfoForLoading(
    val sheetsId: String,
    val titleColumnIndex: Int,
    val subHeaderColumnIndex: Int,
    val categoryColumnIndex: Int,
    val covImgRecordsIndex: Int?=null,
    val numberOfColumns: Int
)

package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

data class ForRecordsState(
    val fileId: Int = -1,
    val sheetId: String = "",
    val numberOfColumns: Int = 0,
    val titleColumnIndex: Int = 0,
    val subHeaderColumnIndex: Int = 0,
    val categoryColumn: Int = 0,
    val covImgRecordsIndex: Int?= null,
    val isAllCategoriesSelected: Boolean = true,
    val listOfSelectedCategories: List<String> = listOf()
)


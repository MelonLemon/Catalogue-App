package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record


import com.melonlemon.catalogueapp.feature_catalogue.domain.model.ColumnInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo

data class AddNewRecordState(
    val name: String = "",
    val urlRecord: String = "",
    val listOfColumns: List<ColumnInfo> = listOf(),
    val tagCheckStatus: Boolean = false,
    val newTag: String = "",
    val listOfTags:  List<SelectedCategoryInfo> = listOf(),
)

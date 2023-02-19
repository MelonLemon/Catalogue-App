package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo

data class FileScreenState(
    val title: String = "",
    val searchText: String = "",
    val listOfTags: List<CategoryInfo> = listOf(),
    val isAllTagsSelected: Boolean = true,
    val listOfSelectedTagsId: List<Int> = listOf(),
    val listOfRecords: List<CardInfo> = listOf(),
    val selectedRecordFullInfo: RecordInfo?=null
)

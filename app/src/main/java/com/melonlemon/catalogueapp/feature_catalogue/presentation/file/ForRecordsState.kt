package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

data class ForRecordsState(
    val fileId: Int = -1,
    val isAllTagsSelected: Boolean = true,
    val listOfSelectedTags: List<String> = listOf()
)

package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record


import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo

data class TagsRecordState(
    val tagCheckStatus: Boolean = false,
    val newTag: String = "",
    val listOfTags:  List<String> = listOf(),
    val listOfSelectedTagsIndex:  List<Int> = listOf()
)

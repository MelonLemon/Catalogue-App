package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo

data class FileInfoState(
    val title: String = "",
    val listOfTags: List<CategoryInfo> = listOf()
)

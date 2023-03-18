package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo

data class AddEditFileState(
    val listOfFolders:  List<CategoryInfo> = listOf(),
    val newTag: String = "",
    val tagCheckStatus: Boolean = false,
    val coverImgCheckStatus: Boolean = false,
)

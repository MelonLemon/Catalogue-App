package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo

data class AddEditFileState(
    val listOfFolders:  List<CategoryInfo> = listOf(),
    val newTag: String = "",
    val tagCheckStatus: Boolean = false,
    val newColumn: String = "",
    val pathCheckStatus: Boolean = false,
    val coverImgCheckStatus: Boolean = false,
)

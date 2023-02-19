package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo

data class AddEditFileState(
    val id: Int = -1,
    val name: String = "",
    val listOfFolders:  List<CategoryInfo> = listOf(),
    val listOfTags:  List<CategoryInfo> = listOf(),
    val newTag: String = "",
    val tagCheckStatus: Boolean = false,
    val selectedFolderId: Int = 0,
    val listOfColumns: List<CategoryInfo> = listOf(),
    val newColumn: String = "",
    val urlPath: String = "",
    val pathCheckStatus: Boolean = false,
    val urlCoverImage: String = "",
    val coverImgCheckStatus: Boolean = false,
)

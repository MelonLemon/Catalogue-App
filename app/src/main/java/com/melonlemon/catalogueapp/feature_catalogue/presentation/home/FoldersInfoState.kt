package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.CheckStatusAddStr

data class FoldersInfoState(
    val listOfFolders: List<CategoryInfo> = listOf(),
    val newFolder: String = "",
    val folderAddStatus: CheckStatusAddStr = CheckStatusAddStr.UnCheckedStatus
)

package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo

data class HomeScreenState(
    val searchText: String = "",
    val listOfFolders: List<CategoryInfo> = listOf(),
    val selectedFolderId: Int = -1,
    val listOfFiles: List<CardInfo> = listOf()
)

package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeScreenEvents

sealed class FileScreenEvents{
    data class OnSearchTextChanged(val text: String): FileScreenEvents()
    object OnCancelSearchClick: FileScreenEvents()
    object OnAllTagsClick: FileScreenEvents()
    data class OnTagClick(val tag: String): FileScreenEvents()

}

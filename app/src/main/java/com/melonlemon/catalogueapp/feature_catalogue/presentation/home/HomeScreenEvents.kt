package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

sealed class HomeScreenEvents{
    data class OnSearchTextChanged(val text: String): HomeScreenEvents()
    object OnCancelSearchClick: HomeScreenEvents()
    data class OnCategoryClick(val id: Int): HomeScreenEvents()
    data class OnDeleteToggleBtnClick(val status: Boolean): HomeScreenEvents()
    data class OnDeleteChosenClick(val id: Int, val status: Boolean): HomeScreenEvents()
    object OnDeleteBtnClick: HomeScreenEvents()
    object OnCancelBtnClick: HomeScreenEvents()
    object OnDeleteCompleteClick: HomeScreenEvents()
}

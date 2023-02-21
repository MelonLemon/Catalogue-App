package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

sealed class HomeScreenEvents{
    data class OnSearchTextChanged(val text: String): HomeScreenEvents()
    object OnCancelSearchClick: HomeScreenEvents()
    data class OnCategoryClick(val id: Int): HomeScreenEvents()
}
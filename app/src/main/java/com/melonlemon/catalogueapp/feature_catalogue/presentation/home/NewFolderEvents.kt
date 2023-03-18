package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

sealed class NewFolderEvents{
    data class OnNameChanged(val text: String): NewFolderEvents()
    object OnAddButtonClick: NewFolderEvents()
    object OnAddComplete: NewFolderEvents()
    data class DeleteFolder(val id: Int): NewFolderEvents()
    object OnFolderDeleteComplete: NewFolderEvents()
}

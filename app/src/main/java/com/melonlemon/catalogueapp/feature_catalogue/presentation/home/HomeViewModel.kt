package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases


class HomeViewModel(
    useCases: CatalogueUseCases
): ViewModel() {

    private val _homeScreenState = mutableStateOf(HomeScreenState())
    val homeScreenState: State<HomeScreenState> = _homeScreenState

    private val _newFolder = mutableStateOf("")
    val newFolder: State<String> = _newFolder

    init {
    }

    fun homeScreenEvents(event: HomeScreenEvents){
        when(event) {
            is HomeScreenEvents.OnSearchTextChanged -> {

            }
            is HomeScreenEvents.OnCancelSearchClick -> {

            }
            is HomeScreenEvents.OnCategoryClick -> {
                if(event.id != homeScreenState.value.selectedFolderId){

                }
            }
            is HomeScreenEvents.AddTagToSearch -> {

            }
            is HomeScreenEvents.AddNewFolder -> {

            }
        }
    }

    fun newFolderScreenEvents(event: NewFolderEvents){
        when(event) {
            is NewFolderEvents.OnNameChanged -> {

            }
            is NewFolderEvents.OnAddButtonClick -> {

            }

        }
    }


}
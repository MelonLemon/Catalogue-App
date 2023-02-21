package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FilterFields
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeViewModel(
    private val useCases: CatalogueUseCases
): ViewModel() {

    private val _foldersInfoState = MutableStateFlow(FoldersInfoState())
    val foldersInfoState = _foldersInfoState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedFolderId = MutableStateFlow(-1)
    val selectedFolderId = _selectedFolderId.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    private val _listOfFiles = selectedFolderId.flatMapLatest{ folderId ->
        useCases.getFiles(folderId) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList())

    @OptIn(FlowPreview::class)
    val listOfFiles = searchText
        .debounce(500L)
        .combine(_listOfFiles)
    { searchText, listOfFiles  ->
        useCases.getFilteredList(
            listCards = listOfFiles,
            searchText = searchText
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _listOfFiles.value
    )

    init {
        viewModelScope.launch {
            val listOfFolders = useCases.getFolders()
            _foldersInfoState.value = foldersInfoState.value.copy(
                listOfFolders = listOfFolders
            )
        }
    }

    fun homeScreenEvents(event: HomeScreenEvents){
        when(event) {
            is HomeScreenEvents.OnSearchTextChanged -> {
                _searchText.value = event.text
            }
            is HomeScreenEvents.OnCancelSearchClick -> {
                _searchText.value = ""
            }
            is HomeScreenEvents.OnCategoryClick -> {
                _selectedFolderId.value = event.id
            }
        }
    }

    fun newFolderScreenEvents(event: NewFolderEvents){
        when(event) {
            is NewFolderEvents.OnNameChanged -> {

                _foldersInfoState.value = foldersInfoState.value.copy(
                    newFolder = event.text
                )
            }
            is NewFolderEvents.OnAddButtonClick -> {
                viewModelScope.launch {
                    useCases.addNewFolder(foldersInfoState.value.newFolder)
                    val newFolderList = useCases.getFolders()
                    _foldersInfoState.value = foldersInfoState.value.copy(
                        newFolder = " ",
                        listOfFolders = newFolderList
                    )
                }

            }

        }
    }


}
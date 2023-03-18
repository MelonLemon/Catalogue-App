package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.CheckStatusAddStr
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val useCases: CatalogueUseCases
): ViewModel() {

    private val _foldersInfoState = MutableStateFlow(FoldersInfoState())
    val foldersInfoState = _foldersInfoState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _deleteState = MutableStateFlow(DeleteFileState())
    val deleteState = _deleteState.asStateFlow()

    private val _deleteFolderCheckState = MutableStateFlow<TransactionCheckStatus>(TransactionCheckStatus.UnCheckedStatus)
    val deleteFolderCheckState = _deleteFolderCheckState.asStateFlow()

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
        useCases.getFilteredFiles(
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
            is HomeScreenEvents.OnDeleteToggleBtnClick -> {
                _deleteState.value = deleteState.value.copy(
                    deleteState = event.status
                )
            }
            is HomeScreenEvents.OnDeleteBtnClick -> {
                viewModelScope.launch {
                    val result = useCases.deleteFiles(deleteState.value.deleteList)
                    if(result== TransactionCheckStatus.SuccessStatus){
                        _deleteState.value = deleteState.value.copy(
                            deleteState = false,
                            deleteList = emptyList(),
                            deleteCheckStatus = result
                        )
                    } else {
                        _deleteState.value = deleteState.value.copy(
                            deleteCheckStatus = result
                        )
                    }

                }

            }
            is HomeScreenEvents.OnCancelBtnClick -> {
                _deleteState.value = deleteState.value.copy(
                    deleteState = false,
                    deleteList = emptyList(),
                    deleteCheckStatus = TransactionCheckStatus.UnCheckedStatus
                )
            }
            is HomeScreenEvents.OnDeleteChosenClick -> {
                val newList = deleteState.value.deleteList.toMutableList()
                if(event.status) newList.add(event.id) else newList.remove(event.id)
                _deleteState.value = deleteState.value.copy(
                    deleteList = newList
                )
            }
            is HomeScreenEvents.OnDeleteCompleteClick -> {
                _deleteState.value = deleteState.value.copy(
                    deleteCheckStatus = TransactionCheckStatus.UnCheckedStatus
                )
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
                    val status = useCases.addNewFolder(
                        foldersInfoState.value.newFolder,
                        foldersInfoState.value.listOfFolders)
                    if(status== CheckStatusAddStr.SuccessStatus){
                        val newFolderList = useCases.getFolders()
                        _foldersInfoState.value = foldersInfoState.value.copy(
                            newFolder = " ",
                            listOfFolders = newFolderList,
                            folderAddStatus = status
                        )
                    } else {
                        _foldersInfoState.value = foldersInfoState.value.copy(
                            folderAddStatus = status
                        )
                    }
                }
            }

            is NewFolderEvents.OnAddComplete -> {
                _foldersInfoState.value = foldersInfoState.value.copy(
                    folderAddStatus = CheckStatusAddStr.UnCheckedStatus
                )
            }
            is NewFolderEvents.DeleteFolder -> {
                viewModelScope.launch {
                    val result = useCases.deleteFolder(event.id)
                }
            }

            is NewFolderEvents.OnFolderDeleteComplete -> {
                _deleteFolderCheckState.value = TransactionCheckStatus.UnCheckedStatus
            }



        }
    }


}
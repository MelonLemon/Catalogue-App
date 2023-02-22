package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditFileViewModel(
    private val useCases: CatalogueUseCases
): ViewModel() {

    private val _addEditFileState = MutableStateFlow(AddEditFileState())
    val addEditFileState = _addEditFileState.asStateFlow()

    private val _fileInfo = MutableStateFlow(FileInfo(
        id = -1,
        name = "",
        folderId = 1,
        urlPath = "",
        urlCoverImage = "",
        columns = listOf(),
        tags = listOf()
    ))
    val fileInfo = _fileInfo.asStateFlow()

    private val _saveNewFile = MutableStateFlow(false)
    val saveNewFile = _saveNewFile.asStateFlow()

    init{
        viewModelScope.launch {
            val listOfFolders = useCases.getFolders()
            _addEditFileState.value = addEditFileState.value.copy(
                listOfFolders = listOfFolders
            )
        }
    }

    fun addEditFileEvents(event: AddEditFileEvents){
        when(event) {
            is AddEditFileEvents.OnNameChanged -> {
                _fileInfo.value = fileInfo.value.copy(
                    name = event.name
                )
            }
            is AddEditFileEvents.OnFolderClick -> {
                _fileInfo.value = fileInfo.value.copy(
                    folderId = event.id
                )
            }
            is AddEditFileEvents.OnUrlPathChange -> {
                _addEditFileState.value = addEditFileState.value.copy(

                )
            }
            is AddEditFileEvents.OnUrlCoverImgChange -> {
                _addEditFileState.value = addEditFileState.value.copy(

                )
            }
            is AddEditFileEvents.OnNewColumnNameChanged -> {
                _addEditFileState.value = addEditFileState.value.copy(
                    newColumn = event.name
                )
            }
            is AddEditFileEvents.OnColumnAddBtnClick -> {
                val newListColumns = fileInfo.value.columns.toMutableList()
                newListColumns.add(addEditFileState.value.newColumn)
                _addEditFileState.value = addEditFileState.value.copy(
                    newColumn = ""
                )
                _fileInfo.value = fileInfo.value.copy(
                    columns = newListColumns
                )
            }
            is AddEditFileEvents.OnNewTagNameChanged -> {
                _addEditFileState.value = addEditFileState.value.copy(
                    newTag = event.name
                )
            }
            is AddEditFileEvents.OnTagAddBtnClick -> {

                val newListTags = fileInfo.value.tags.toMutableList()
                newListTags.add(addEditFileState.value.newTag)
                _addEditFileState.value = addEditFileState.value.copy(
                    newTag = ""
                )
                _fileInfo.value = fileInfo.value.copy(
                    tags = newListTags
                )

            }

            is AddEditFileEvents.OnSaveFabClick -> {
                viewModelScope.launch {
                    useCases.addNewFile(fileInfo.value)
                    _saveNewFile.value = true
                }

            }


        }
    }

}
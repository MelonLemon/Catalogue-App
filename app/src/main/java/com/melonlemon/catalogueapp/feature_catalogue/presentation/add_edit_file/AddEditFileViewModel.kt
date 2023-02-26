package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddEditFileViewModel(
    private val useCases: CatalogueUseCases
): ViewModel() {

    private val _addEditFileState = MutableStateFlow(AddEditFileState())
    val addEditFileState = _addEditFileState.asStateFlow()

    private val _authenticationState = MutableStateFlow(AuthenticationState())
    val authenticationState = _authenticationState.asStateFlow()

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
            //Authentication
            is AddEditFileEvents.OnNewExistingFileClick -> {
                if(event.typeFile !=authenticationState.value.typeFile){
                    _authenticationState.value = authenticationState.value.copy(
                        typeFile = event.typeFile
                    )
                }
            }
            is AddEditFileEvents.OnNextBtnClick -> {
                //Check Authentications and place rules
            }
            is AddEditFileEvents.AuthenticationStatusRefresh -> {
                _authenticationState.value = authenticationState.value.copy(
                    authenticationStatus = AuthenticationStatus.UnCheckedStatus
                )
            }
            is AddEditFileEvents.UrlPathFolderNewChange -> {
                _authenticationState.value = authenticationState.value.copy(
                    urlPathFolderNew = event.urlString
                )
            }
            is AddEditFileEvents.UrlPathNameNewChange -> {
                _authenticationState.value = authenticationState.value.copy(
                    urlPathNameNew = event.name
                )
            }
            is AddEditFileEvents.OnPublicPrivateRightsClick -> {
                _authenticationState.value = authenticationState.value.copy(
                    visibilityLevelNew = event.rightsType
                )
            }
            is AddEditFileEvents.FullUrlExistingFileChange -> {
                _authenticationState.value = authenticationState.value.copy(
                    fullUrlExistingFile = event.urlString
                )
            }
            is AddEditFileEvents.OnNewEmailChanged -> {
                _authenticationState.value = authenticationState.value.copy(
                    newEmailNewFile = event.name
                )
            }

            is AddEditFileEvents.OnNewEmailAddBtnClick -> {

            }
            is AddEditFileEvents.OnNewPermissionLevelClick -> {
                _authenticationState.value = authenticationState.value.copy(
                    newPermissionLevel = event.permissionLevel
                )
            }


            //Content
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
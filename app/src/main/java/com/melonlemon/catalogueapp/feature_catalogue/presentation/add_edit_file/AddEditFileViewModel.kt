package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.InfoForLoading
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFileViewModel @Inject constructor(
    private val useCases: CatalogueUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _addEditFileState = MutableStateFlow(AddEditFileState())
    val addEditFileState = _addEditFileState.asStateFlow()

    private val _authenticationState = MutableStateFlow(AuthenticationState())
    val authenticationState = _authenticationState.asStateFlow()

    private val _fileInfo = MutableStateFlow(Files(
        id = -1,
        name="",
        folderId = -1,
        sheetsId = "",
        numColumns = 0,
        titleColumnIndex = 0,
        subHeaderColumnIndex = 0,
        categoryColumnIndex = 0,
        covImgRecColumnIndex = null,
        coverImg = "",
        tags = emptyList()
    ))
    val fileInfo = _fileInfo.asStateFlow()

    private val _fileColumns = MutableStateFlow<List<String>>(emptyList())
    val fileColumns = _fileColumns.asStateFlow()

    private val _columnsDialogState = MutableStateFlow(ColumnsDialogState())
    val columnsDialogState = _columnsDialogState.asStateFlow()



    private val _saveNewFile = MutableStateFlow(false)
    val saveNewFile = _saveNewFile.asStateFlow()

    init{
        val fileId = savedStateHandle.get<Int>("fileId")
        viewModelScope.launch {
            val listOfFolders = useCases.getFolders()
            _addEditFileState.value = addEditFileState.value.copy(
                listOfFolders = listOfFolders
            )
            if(fileId!=-1 && fileId!=null){
                val file = useCases.getFileById(fileId)
                _fileInfo.value = file
            }
        }
    }

    fun addEditFileEvents(event: AddEditFileEvents){
        when(event) {
            //Authentication
            is AddEditFileEvents.OnNextBtnClick -> {
                if(authenticationState.value.checkStatusRights && authenticationState.value.numColumn!=0){
                    _authenticationState.value = authenticationState.value.copy(
                        authenticationStatus = AuthenticationStatus.SuccessStatus
                    )
                } else {
                    _authenticationState.value = authenticationState.value.copy(
                        authenticationStatus = if(!authenticationState.value.checkStatusRights) AuthenticationStatus.NoRightsFailStatus else
                            AuthenticationStatus.NoNumberColumnStatus
                    )
                }
            }
            is AddEditFileEvents.AuthenticationStatusRefresh -> {
                _authenticationState.value = authenticationState.value.copy(
                    authenticationStatus = AuthenticationStatus.UnCheckedStatus
                )
            }

            is AddEditFileEvents.FullUrlExistingFileChange -> {
                _authenticationState.value = authenticationState.value.copy(
                    fullUrlExistingFile = event.urlString
                )
            }
            is AddEditFileEvents.OnNumColumnChange -> {
                _authenticationState.value = authenticationState.value.copy(
                    numColumn = event.num
                )
            }
            is AddEditFileEvents.OnPathCheckBtnClick -> {
                viewModelScope.launch {
                    val result = useCases.getFirstRow(
                        sheetsId = fileInfo.value.sheetsId,
                        number = fileInfo.value.numColumns
                    )
                    _authenticationState.value = authenticationState.value.copy(
                        checkStatusRights = result.first == TransactionCheckStatus.SuccessStatus
                    )
                }
            }
            is AddEditFileEvents.OnColumnCheckBtnClick -> {
                if(authenticationState.value.numColumn!=0){
                    viewModelScope.launch {
                        val result = useCases.getFirstRow(
                            sheetsId = fileInfo.value.sheetsId,
                            number = fileInfo.value.numColumns
                        )
                        _authenticationState.value = authenticationState.value.copy(
                            checkStatusRights = result.first == TransactionCheckStatus.SuccessStatus
                        )
                        val columnsList = if(result.first == TransactionCheckStatus.SuccessStatus)
                            result.second else emptyList()
                        _fileColumns.value = columnsList ?:emptyList()
                    }
                }
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
                    coverImgCheckStatus = event.urlString != ""
                )
            }
            is AddEditFileEvents.OnColumnTypeClick -> {
                _columnsDialogState.value = columnsDialogState.value.copy(
                    selectedIndex = event.index,
                    columnType = event.columnType
                )
            }
            is AddEditFileEvents.OnColumnDialogClick -> {
                _columnsDialogState.value = columnsDialogState.value.copy(
                    selectedIndex = event.index
                )
            }
            is AddEditFileEvents.OnCancelDialogClick -> {
                _columnsDialogState.value = columnsDialogState.value.copy(
                    selectedIndex = 0
                )
            }
            is AddEditFileEvents.OnSaveDialogClick -> {
                when(columnsDialogState.value.columnType){
                    is ColumnType.TitleColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            titleColumnIndex = columnsDialogState.value.selectedIndex
                        )
                    }
                    is ColumnType.SubHeaderColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            subHeaderColumnIndex = columnsDialogState.value.selectedIndex
                        )
                    }
                    is ColumnType.CategoryColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            categoryColumnIndex = columnsDialogState.value.selectedIndex
                        )
                    }
                    is ColumnType.CovImgRecordColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            covImgRecColumnIndex = columnsDialogState.value.selectedIndex
                        )
                    }
                }
            }

            is AddEditFileEvents.OnNewTagNameChanged -> {
                _addEditFileState.value = addEditFileState.value.copy(
                    newTag = event.name
                )
            }
            is AddEditFileEvents.OnTagAddBtnClick -> {
                if(addEditFileState.value.newTag.isNotBlank()){
                    val newListTags = fileInfo.value.tags.toMutableList()
                    newListTags.add(addEditFileState.value.newTag)

                    if(!addEditFileState.value.tagCheckStatus) {
                        _addEditFileState.value = addEditFileState.value.copy(
                            newTag = "",
                            tagCheckStatus = true
                        )
                    } else {
                        _addEditFileState.value = addEditFileState.value.copy(
                            newTag = ""
                        )
                    }
                    _fileInfo.value = fileInfo.value.copy(
                        tags = newListTags
                    )
                }
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
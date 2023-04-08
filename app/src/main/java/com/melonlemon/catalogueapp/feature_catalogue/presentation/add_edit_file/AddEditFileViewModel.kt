package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.ValidationUrlCheckStatus
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
        id = null,
        name="",
        folderId = -1,
        sheetsId = "",
        sheetsName = "",
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

    private val _rightsCheck = MutableStateFlow<ValidationUrlCheckStatus>(ValidationUrlCheckStatus.UnCheckedStatus)
    val rightsCheck = _rightsCheck.asStateFlow()


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
                val result = useCases.checkUrlValidation(
                    sheetsId = fileInfo.value.sheetsId,
                    sheetsName = fileInfo.value.sheetsName
                )
                _authenticationState.value = authenticationState.value.copy(
                    checkStatusRights = result == ValidationUrlCheckStatus.SuccessStatus,
                )
            } else {
                if(addEditFileState.value.listOfFolders.isNotEmpty()) {
                    _fileInfo.value = fileInfo.value.copy(
                        folderId = addEditFileState.value.listOfFolders[0].id
                    )
                }

            }


        }
    }

    fun addEditFileEvents(event: AddEditFileEvents){
        when(event) {
            //Authentication
            is AddEditFileEvents.OnNextBtnClick -> {

                if(authenticationState.value.checkStatusRights && _fileInfo.value.numColumns!=0){
                        viewModelScope.launch {
                            val result = useCases.getFirstRow(
                                sheetsId = fileInfo.value.sheetsId,
                                sheetsName = fileInfo.value.sheetsName,
                                number = fileInfo.value.numColumns
                            )
                            if(result.first == TransactionCheckStatus.SuccessStatus){
                                _authenticationState.value = authenticationState.value.copy(
                                    checkStatusRights = true,
                                    checkStatusNumColumn = true
                                )
                                val columnsList = result.second
                                _fileColumns.value = if(columnsList!!.isEmpty()) List(fileInfo.value.numColumns){""}
                                else columnsList
                                _authenticationState.value = authenticationState.value.copy(
                                    authenticationStatus = AuthenticationStatus.SuccessStatus
                                )
                                _addEditFileState.value = addEditFileState.value.copy(
                                    coverImgCheckStatus = fileInfo.value.coverImg != ""
                                )
                                val maxIndex = fileInfo.value.numColumns - 1
                                _fileInfo.value = fileInfo.value.copy(
                                    titleColumnIndex = if(fileInfo.value.titleColumnIndex > maxIndex)
                                        0 else fileInfo.value.titleColumnIndex,
                                    subHeaderColumnIndex = if(fileInfo.value.subHeaderColumnIndex > maxIndex )
                                        0 else fileInfo.value.subHeaderColumnIndex,
                                    categoryColumnIndex = if(fileInfo.value.categoryColumnIndex > maxIndex)
                                        0 else fileInfo.value.categoryColumnIndex,
                                    covImgRecColumnIndex = if(fileInfo.value.covImgRecColumnIndex != null)
                                        if(fileInfo.value.categoryColumnIndex > maxIndex)
                                        0 else fileInfo.value.covImgRecColumnIndex
                                    else null
                                )

                            } else {
                                //not success- change
                                _authenticationState.value = authenticationState.value.copy(
                                    checkStatusRights = result.first == TransactionCheckStatus.SuccessStatus,
                                    checkStatusNumColumn = result.first == TransactionCheckStatus.SuccessStatus
                                )
                                val columnsList = if(result.first == TransactionCheckStatus.SuccessStatus)
                                    result.second else emptyList()
                                _fileColumns.value = columnsList ?:emptyList()

                                _authenticationState.value = authenticationState.value.copy(
                                    authenticationStatus = AuthenticationStatus.SuccessStatus
                                )
                            }


                        }


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

            is AddEditFileEvents.OnSheetsIdChange -> {
                _fileInfo.value = fileInfo.value.copy(
                    sheetsId = event.idString
                )
                _authenticationState.value = authenticationState.value.copy(
                    checkStatusRights = false
                )
            }
            is AddEditFileEvents.OnSheetsNameChange -> {
                _fileInfo.value = fileInfo.value.copy(
                    sheetsName = event.name
                )
                _authenticationState.value = authenticationState.value.copy(
                    checkStatusRights = false
                )
            }
            is AddEditFileEvents.OnRightCheckRefresh -> {
                _rightsCheck.value = ValidationUrlCheckStatus.UnCheckedStatus
            }


            is AddEditFileEvents.OnNumColumnChange -> {
                _fileInfo.value = fileInfo.value.copy(
                    numColumns = event.num
                )
            }
            is AddEditFileEvents.OnPathCheckBtnClick -> {
                viewModelScope.launch {
                    val result = useCases.checkUrlValidation(
                        sheetsId = fileInfo.value.sheetsId,
                        sheetsName = fileInfo.value.sheetsName
                    )
                    _authenticationState.value = authenticationState.value.copy(
                        checkStatusRights = result == ValidationUrlCheckStatus.SuccessStatus
                    )
                    _rightsCheck.value = result
                }
            }
            is AddEditFileEvents.OnColumnCheckBtnClick -> {
                if(fileInfo.value.numColumns!=0){
                    viewModelScope.launch {
                        val result = useCases.getFirstRow(
                            sheetsId = fileInfo.value.sheetsId,
                            sheetsName = fileInfo.value.sheetsName,
                            number = fileInfo.value.numColumns
                        )
                        _authenticationState.value = authenticationState.value.copy(
                            checkStatusRights = result.first == TransactionCheckStatus.SuccessStatus,
                            checkStatusNumColumn = result.first == TransactionCheckStatus.SuccessStatus
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
                _fileInfo.value = fileInfo.value.copy(
                    coverImg = event.urlString
                )
            }
            is AddEditFileEvents.OnColumnTypeClick -> {
                _columnsDialogState.value = columnsDialogState.value.copy(
                    selectedIndex = event.index,
                    columnType = event.columnType
                )
            }


            is AddEditFileEvents.OnSaveDialogClick -> {
                when(event.columnType){
                    is ColumnType.TitleColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            titleColumnIndex = event.index
                        )
                    }
                    is ColumnType.SubHeaderColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            subHeaderColumnIndex = event.index
                        )
                    }
                    is ColumnType.CategoryColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            categoryColumnIndex = event.index
                        )
                    }
                    is ColumnType.CovImgRecordColumn -> {
                        _fileInfo.value = fileInfo.value.copy(
                            covImgRecColumnIndex = event.index
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
                    val isDistinct = addEditFileState.value.newTag.lowercase() !in fileInfo.value.tags.map { it.lowercase() }
                    if(addEditFileState.value.newTag.isNotBlank() && isDistinct ){
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
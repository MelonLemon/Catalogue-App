package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(
    private val useCases: CatalogueUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _fileInfoState = MutableStateFlow(FileInfoState())
    val fileInfoState = _fileInfoState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _forRecordsState = MutableStateFlow(ForRecordsState())
    val forRecordsState = _forRecordsState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _listOfRecords = forRecordsState.flatMapLatest{ forRecordsState ->
        useCases.getRecords(
            forRecordsState = forRecordsState)
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList())

    @OptIn(FlowPreview::class)
    val listOfRecords = searchText
        .debounce(500L)
        .combine(_listOfRecords)
    { searchText, listOfRecords  ->
        useCases.getFilteredList(
            listCards = listOfRecords,
            searchText = searchText
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _listOfRecords.value
    )

    private val _selectedRecordFullInfo = MutableStateFlow<List<String>>(emptyList())
    val selectedRecordFullInfo = _selectedRecordFullInfo.asStateFlow()

    private val _columnsTitles = MutableStateFlow<List<String>>(emptyList())
    val columnsTitles = _columnsTitles.asStateFlow()

    init {
        val fileId = savedStateHandle.get<Int>("fileId")
        val title = savedStateHandle.get<String>("title")
        viewModelScope.launch {
            if(fileId!=null){

                val infoForLoading = useCases.getInfoForLoading(fileId)
                //separate changing part with not changing part
                _forRecordsState.value = forRecordsState.value.copy(
                    fileId = fileId,
                    sheetId = infoForLoading.sheetsId,
                    titleColumnIndex = infoForLoading.titleColumnIndex,
                    subHeaderColumnIndex = infoForLoading.subHeaderColumnIndex,
                    categoryColumn = infoForLoading.categoryColumnIndex,
                    numberOfColumns = infoForLoading.numberOfColumns
                )
                val listCategories = useCases.getFileCategories(
                    sheetId =  infoForLoading.sheetsId,
                    categoryColumnIndex = infoForLoading.categoryColumnIndex
                )
                _fileInfoState.value = fileInfoState.value.copy(
                    title = title?:"",
                    listOfCategories = listCategories
                )
            }
        }

    }

    fun fileScreenEvents(event: FileScreenEvents){
        when(event) {
            is FileScreenEvents.OnSearchTextChanged -> {
                _searchText.value = event.text
            }
            is FileScreenEvents.OnCancelSearchClick -> {
                _searchText.value = ""
            }
            is FileScreenEvents.OnAllTagsClick -> {
                _forRecordsState.value = forRecordsState.value.copy(
                    isAllCategoriesSelected = !forRecordsState.value.isAllCategoriesSelected
                )
            }
            is FileScreenEvents.OnTagClick -> {
                val newList = forRecordsState.value.listOfSelectedCategories.toMutableList()
                if(event.tag in newList) newList.remove(event.tag) else newList.add(event.tag)
                _forRecordsState.value = forRecordsState.value.copy(
                    listOfSelectedCategories = newList
                )
            }
            is FileScreenEvents.OnRecordSelect -> {
                viewModelScope.launch {
                    _columnsTitles.value  = useCases.getFileColumnTitles(
                        sheetsId = forRecordsState.value.sheetId,
                        numColumns = forRecordsState.value.numberOfColumns
                    )
                    _selectedRecordFullInfo.value = listOfRecords.value[event.index]
                }

            }

        }
    }





}
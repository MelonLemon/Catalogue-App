package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.presentation.record.RecordScreenEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FileViewModel(
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

    init {
//        val fileId = savedStateHandle.get<Int>("fileId")
        val fileId = 3
        viewModelScope.launch {
            if(fileId!=null){
                _fileInfoState.value = useCases.getFileInfo(fileId)
                _forRecordsState.value = forRecordsState.value.copy(
                    fileId = fileId
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
                    isAllTagsSelected = !forRecordsState.value.isAllTagsSelected
                )
            }
            is FileScreenEvents.OnTagClick -> {
                val newList = forRecordsState.value.listOfSelectedTags.toMutableList()
                if(event.tag in newList) newList.remove(event.tag) else newList.add(event.tag)
                _forRecordsState.value = forRecordsState.value.copy(
                    listOfSelectedTags = newList
                )
            }
        }
    }





}
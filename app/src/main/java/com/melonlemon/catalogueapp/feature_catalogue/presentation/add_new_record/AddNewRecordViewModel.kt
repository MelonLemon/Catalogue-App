package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddNewRecordViewModel(
    private val useCases: CatalogueUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _tagsRecordState = MutableStateFlow(TagsRecordState())
    val tagsRecordState = _tagsRecordState.asStateFlow()

    private val _recordInfo = MutableStateFlow(RecordInfo(
        id = -1,
        name = "",
        urlString = "",
        columnsInfo = listOf(),
        tags = listOf()
    ))
    val recordInfo = _recordInfo.asStateFlow()

    private val _saveNewRecord = MutableStateFlow(false)
    val saveNewRecord = _saveNewRecord.asStateFlow()

    init {
        val fileId = savedStateHandle.get<Int>("fileId")
        viewModelScope.launch {
            if(fileId!=null){
                val columns = useCases.getFileColumns(fileId)
                val tagRecords = useCases.getTagsRecords(fileId)
                _tagsRecordState.value = tagsRecordState.value.copy(
                    listOfTags = tagRecords
                )
                _recordInfo.value = recordInfo.value.copy(
                    columnsInfo = columns
                )
            }

        }

    }

    fun addEditRecordEvents(event: AddNewRecordEvents){
        when(event) {
            is AddNewRecordEvents.OnUrlPathChange -> {
                _recordInfo.value = recordInfo.value.copy(
                    urlString = event.urlString
                )
            }
            is AddNewRecordEvents.OnNameChange -> {
                _recordInfo.value = recordInfo.value.copy(
                    name = event.name
                )
            }
            is AddNewRecordEvents.OnColumnTextChange -> {
                val newListColumns = recordInfo.value.columnsInfo.toMutableList()
                newListColumns[event.index] = newListColumns[event.index].copy(
                    text = event.text
                )
                _recordInfo.value = recordInfo.value.copy(
                    columnsInfo = newListColumns
                )
            }
            is AddNewRecordEvents.OnNewTagNameChanged -> {
                _tagsRecordState.value = tagsRecordState.value.copy(
                    newTag = event.name
                )
            }
            is AddNewRecordEvents.OnTagAddBtnClick -> {
                viewModelScope.launch {
                    val newListTags = tagsRecordState.value.listOfTags.toMutableList()
                    newListTags.add(SelectedCategoryInfo(newListTags.size, tagsRecordState.value.newTag, true))
                    _tagsRecordState.value = tagsRecordState.value.copy(
                        newTag = "",
                        listOfTags = newListTags
                    )
                }
            }
            is AddNewRecordEvents.OnCheckStateTagChange -> {
                // Change to Index
                val newListTags = tagsRecordState.value.listOfTags.toMutableList()
                val replaceValue = newListTags.find { it.id == event.id }
                _tagsRecordState.value = tagsRecordState.value.copy(
                    newTag = "",
                    listOfTags = newListTags
                )
            }
            is AddNewRecordEvents.OnSaveFabClick -> {
                viewModelScope.launch {
                    _recordInfo.value = recordInfo.value.copy(
//                        tags = tagsRecordState.value.listOfTags.all{
//                            it.isSelected
//                        }.map {
//                            it.name
//                        }
                    )
                    useCases.addNewRecord(recordInfo.value)
//                    useCases.addTagRecord()
                }
            }

        }
    }

}
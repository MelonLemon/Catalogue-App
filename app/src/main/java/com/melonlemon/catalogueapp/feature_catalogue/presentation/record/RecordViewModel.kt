package com.melonlemon.catalogueapp.feature_catalogue.presentation.record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordViewModel(
    private val useCases: CatalogueUseCases,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _selectedRecordFullInfo = MutableStateFlow(RecordInfo(
        id = -1,
        name = "",
        urlString = "",
        columnsInfo = listOf(),
        tags = listOf()
    ))
    val selectedRecordFullInfo = _selectedRecordFullInfo.asStateFlow()

    private val _saveRecordChanging= MutableStateFlow(false)
    val saveRecordChanging = _saveRecordChanging.asStateFlow()

    init {
        val recordId = savedStateHandle.get<Int>("recordId")
        viewModelScope.launch {
           if(recordId!=null) {
               _selectedRecordFullInfo.value = useCases.getRecord(recordId)
           }
        }


    }

    fun recordScreenEvents(event: RecordScreenEvents){
        when(event) {
            is RecordScreenEvents.OnSelRecordColumnChange -> {
                val newList = selectedRecordFullInfo.value.columnsInfo.toMutableList()
                newList[event.index] = newList[event.index].copy(
                    text = event.text
                )
                _selectedRecordFullInfo.value = selectedRecordFullInfo.value.copy(
                    columnsInfo = newList
                )

            }
            is RecordScreenEvents.SaveRecordChanges -> {
                viewModelScope.launch {
                    useCases.updateRecord(selectedRecordFullInfo.value)
                    _saveRecordChanging.value = true
                }
            }


        }
    }

}
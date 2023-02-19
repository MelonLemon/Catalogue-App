package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases

class AddNewRecordViewModel(
    useCases: CatalogueUseCases
): ViewModel() {

    private val _addEditRecordState = mutableStateOf(AddNewRecordState())
    val addEditRecordState: State<AddNewRecordState> = _addEditRecordState

    init{

    }

    fun addEditRecordEvents(event: AddNewRecordEvents){
        when(event) {
            is AddNewRecordEvents.OnUrlPathChange -> {

            }
            is AddNewRecordEvents.OnNameChange -> {

            }
            is AddNewRecordEvents.OnColumnTextChange -> {

            }
            is AddNewRecordEvents.OnNewTagNameChanged -> {

            }
            is AddNewRecordEvents.OnTagAddBtnClick -> {

            }
            is AddNewRecordEvents.OnCheckStateTagChange -> {

            }


        }
    }

}
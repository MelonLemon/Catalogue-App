package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileScreenEvents
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileScreenState

class AddEditFileViewModel(
    useCases: CatalogueUseCases
): ViewModel() {

    private val _addEditFileState = mutableStateOf(AddEditFileState())
    val addEditFileState: State<AddEditFileState> = _addEditFileState


    fun addEditFileEvents(event: AddEditFileEvents){
        when(event) {
            is AddEditFileEvents.OnNameChanged -> {

            }
            is AddEditFileEvents.OnTagClick -> {

            }
            is AddEditFileEvents.OnUrlPathChange -> {

            }
            is AddEditFileEvents.OnUrlCoverImgChange -> {

            }
            is AddEditFileEvents.OnNewColumnNameChanged -> {

            }
            is AddEditFileEvents.OnColumnAddBtnClick -> {

            }
            is AddEditFileEvents.OnNewTagNameChanged -> {

            }
            is AddEditFileEvents.OnTagAddBtnClick -> {

            }


        }
    }

}
package com.melonlemon.catalogueapp.feature_catalogue.presentation.file

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.CatalogueUseCases

class FileViewModel(
    useCases: CatalogueUseCases
): ViewModel() {

    private val _fileScreenState = mutableStateOf(FileScreenState())
    val fileScreenState: State<FileScreenState> = _fileScreenState

    fun fileScreenEvents(event: FileScreenEvents){
        when(event) {
            is FileScreenEvents.OnSearchTextChanged -> {

            }
            is FileScreenEvents.OnCancelSearchClick -> {

            }
            is FileScreenEvents.OnAllTagsClick -> {

            }
            is FileScreenEvents.OnTagClick -> {

            }
        }
    }

    fun onSelRecordColumnChange(id: Int, text: String){

    }
}
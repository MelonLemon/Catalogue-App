package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_new_record

sealed class AddNewRecordEvents{
    data class OnUrlPathChange(val urlString: String): AddNewRecordEvents()
    data class OnNameChange(val name: String): AddNewRecordEvents()
    data class OnColumnTextChange(val id: Int, val text: String): AddNewRecordEvents()
    data class OnNewTagNameChanged(val name: String): AddNewRecordEvents()
    object OnTagAddBtnClick: AddNewRecordEvents()
    data class OnCheckStateTagChange(val id: Int, val checkState: Boolean): AddNewRecordEvents()
}

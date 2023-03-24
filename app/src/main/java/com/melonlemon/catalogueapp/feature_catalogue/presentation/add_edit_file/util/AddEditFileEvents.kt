package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class AddEditFileEvents{
    //Authentication
    object OnNextBtnClick: AddEditFileEvents()
    object AuthenticationStatusRefresh: AddEditFileEvents()
    data class OnSheetsIdChange(val idString: String): AddEditFileEvents()
    data class OnSheetsNameChange(val name: String): AddEditFileEvents()
    data class OnNumColumnChange(val num: Int): AddEditFileEvents()
    object OnRightCheckRefresh: AddEditFileEvents()
    object OnPathCheckBtnClick: AddEditFileEvents()
    object OnColumnCheckBtnClick: AddEditFileEvents()
    //Content
    data class OnNameChanged(val name: String): AddEditFileEvents()
    data class OnFolderClick(val id: Int): AddEditFileEvents()
    data class OnUrlCoverImgChange(val urlString: String): AddEditFileEvents()
    data class OnColumnDialogClick(val index: Int): AddEditFileEvents()
    object OnCancelDialogClick: AddEditFileEvents()
    object OnSaveDialogClick: AddEditFileEvents()
    data class OnColumnTypeClick(val index: Int, val columnType: ColumnType): AddEditFileEvents()
    data class OnNewTagNameChanged(val name: String): AddEditFileEvents()
    object OnTagAddBtnClick: AddEditFileEvents()
    object OnSaveFabClick: AddEditFileEvents()
}

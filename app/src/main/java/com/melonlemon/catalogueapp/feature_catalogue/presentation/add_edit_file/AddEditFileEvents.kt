package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file

import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileScreenEvents

sealed class AddEditFileEvents{
    data class OnNameChanged(val name: String): AddEditFileEvents()
    data class OnTagClick(val id: Int): AddEditFileEvents()
    data class OnUrlPathChange(val urlString: String): AddEditFileEvents()
    data class OnUrlCoverImgChange(val urlString: String): AddEditFileEvents()
    data class OnNewColumnNameChanged(val name: String): AddEditFileEvents()
    object OnColumnAddBtnClick: AddEditFileEvents()
    data class OnNewTagNameChanged(val name: String): AddEditFileEvents()
    object OnTagAddBtnClick: AddEditFileEvents()
}

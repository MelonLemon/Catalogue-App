package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class AddEditFileEvents{
    //Authentication
    data class OnNewExistingFileClick(val typeFile: TypeFile): AddEditFileEvents()
    object OnNextBtnClick: AddEditFileEvents()
    object AuthenticationStatusRefresh: AddEditFileEvents()
    data class UrlPathFolderNewChange(val urlString: String): AddEditFileEvents()
    data class UrlPathNameNewChange(val name: String): AddEditFileEvents()
    data class OnPublicPrivateRightsClick(val rightsType: VisibilityLevel): AddEditFileEvents()
    data class FullUrlExistingFileChange(val urlString: String): AddEditFileEvents()
    data class OnNewEmailChanged(val name: String): AddEditFileEvents()
    data class OnNewPermissionLevelClick(val permissionLevel: PermissionLevel): AddEditFileEvents()
    object OnNewEmailAddBtnClick: AddEditFileEvents()
    //Content
    data class OnNameChanged(val name: String): AddEditFileEvents()
    data class OnFolderClick(val id: Int): AddEditFileEvents()
    data class OnUrlCoverImgChange(val urlString: String): AddEditFileEvents()
    data class OnNewColumnNameChanged(val name: String): AddEditFileEvents()
    object OnColumnAddBtnClick: AddEditFileEvents()
    data class OnNewTagNameChanged(val name: String): AddEditFileEvents()
    object OnTagAddBtnClick: AddEditFileEvents()
    object OnSaveFabClick: AddEditFileEvents()
}

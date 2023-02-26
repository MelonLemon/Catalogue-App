package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

data class AuthenticationState(
    val typeFile: TypeFile = TypeFile.NewFile,
    val authenticationStatus: AuthenticationStatus = AuthenticationStatus.UnCheckedStatus,
    val pathCheckStatusNew: Boolean = false,
    val pathCheckStatusExist: Boolean = false,
    val urlPathFolderNew: String = "",
    val urlPathNameNew: String = "",
    val fullUrlExistingFile: String = "",
    val visibilityLevelNew: VisibilityLevel = VisibilityLevel.PublicLinkRestrictType,
    val permissionLevelNew: PermissionLevel = PermissionLevel.Viewer,
    val permissionLevelExist: PermissionLevel = PermissionLevel.Viewer,
    val checkStatusRightsExist: Boolean = false,
    val newEmailNewFile: String = "",
    val newPermissionLevel: PermissionLevel = PermissionLevel.Viewer,
    val emailViewerList: List<String> = emptyList(),
    val emailCommenterList: List<String> = emptyList(),
    val emailEditorList: List<String> = emptyList(),
)

package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class PermissionLevel{
    object Owner: PermissionLevel()
    object Editor: PermissionLevel()
    object Commenter: PermissionLevel()
    object Viewer: PermissionLevel()
}

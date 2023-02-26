package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class VisibilityLevel{
    object PublicType: VisibilityLevel()
    object PublicLinkRestrictType: VisibilityLevel()
    object PrivateType: VisibilityLevel()
}



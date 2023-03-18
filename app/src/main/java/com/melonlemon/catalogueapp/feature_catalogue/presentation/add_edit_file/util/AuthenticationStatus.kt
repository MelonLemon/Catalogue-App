package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

sealed class AuthenticationStatus{
    object UnCheckedStatus: AuthenticationStatus()
    object UnknownFailStatus: AuthenticationStatus()
    object NoNumberColumnStatus: AuthenticationStatus()
    object NoRightsFailStatus: AuthenticationStatus()
    object PathFailStatus: AuthenticationStatus()
    object SuccessStatus: AuthenticationStatus()
}

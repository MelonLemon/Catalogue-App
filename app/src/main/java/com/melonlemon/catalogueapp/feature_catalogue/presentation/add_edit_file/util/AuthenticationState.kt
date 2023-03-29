package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

data class AuthenticationState(
    val authenticationStatus: AuthenticationStatus = AuthenticationStatus.UnCheckedStatus,
    val pathCheckStatusExist: Boolean = false,
    val checkStatusRights: Boolean = false,
    val checkStatusNumColumn: Boolean = false,
)

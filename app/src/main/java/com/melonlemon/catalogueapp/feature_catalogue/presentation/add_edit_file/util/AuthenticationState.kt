package com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util

data class AuthenticationState(
    val authenticationStatus: AuthenticationStatus = AuthenticationStatus.UnCheckedStatus,
    val pathCheckStatusExist: Boolean = false,
    val sheetsId: String = "",
    val sheetsName: String = "",
    val checkStatusRights: Boolean = false,
    val checkStatusNumColumn: Boolean = false,
    val numColumn: Int = 0,
)

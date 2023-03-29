package com.melonlemon.catalogueapp.feature_catalogue.domain.util

sealed class ValidationUrlCheckStatus{
    object BlankParameterFailStatus: ValidationUrlCheckStatus()
    object SuccessStatus: ValidationUrlCheckStatus()
    object NoRightsFailStatus: ValidationUrlCheckStatus()
    object BrokenUrlFailStatus: ValidationUrlCheckStatus()
    object UnCheckedStatus: ValidationUrlCheckStatus()
}

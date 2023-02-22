package com.melonlemon.catalogueapp.feature_catalogue.domain.util

sealed class CheckStatusAddStr{
    object BlankFailStatus: CheckStatusAddStr()
    object DuplicateFailStatus: CheckStatusAddStr()
    object SuccessStatus: CheckStatusAddStr()
    object UnKnownFailStatus: CheckStatusAddStr()
    object UnCheckedStatus: CheckStatusAddStr()
}

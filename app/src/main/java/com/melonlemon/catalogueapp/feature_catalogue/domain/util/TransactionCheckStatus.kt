package com.melonlemon.catalogueapp.feature_catalogue.domain.util

sealed class TransactionCheckStatus{
    object BlankParameterFailStatus: TransactionCheckStatus()
    object SuccessStatus: TransactionCheckStatus()
    object UnKnownFailStatus: TransactionCheckStatus()
    object UnCheckedStatus: TransactionCheckStatus()
}

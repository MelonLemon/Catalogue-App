package com.melonlemon.catalogueapp.feature_catalogue.domain.util

import androidx.annotation.StringRes
import com.melonlemon.catalogueapp.R

sealed class TransactionCheckStatus(@StringRes val message: Int){
    object BlankParameterFailStatus: TransactionCheckStatus(R.string.empty_name)
    object SuccessStatus: TransactionCheckStatus(R.string.success)
    object UnKnownFailStatus: TransactionCheckStatus(R.string.unknown_error)
    object UnCheckedStatus: TransactionCheckStatus(R.string.unchecked)
}

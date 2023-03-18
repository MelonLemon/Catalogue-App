package com.melonlemon.catalogueapp.feature_catalogue.presentation.home

import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus

data class DeleteFileState(
    val deleteState: Boolean = false,
    val deleteList: List<Int> = emptyList(),
    val deleteCheckStatus: TransactionCheckStatus = TransactionCheckStatus.UnCheckedStatus
)

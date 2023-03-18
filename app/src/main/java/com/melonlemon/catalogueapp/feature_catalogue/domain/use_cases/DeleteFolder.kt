package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus

class DeleteFolder (
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(folderId: Int): TransactionCheckStatus {

        try{
            repository.deleteFolder(folderId)
        } catch (e: Exception){
            return TransactionCheckStatus.UnKnownFailStatus
        }
        return TransactionCheckStatus.SuccessStatus

    }
}
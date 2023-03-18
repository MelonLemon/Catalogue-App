package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus

class DeleteFiles(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(filesId: List<Int>): TransactionCheckStatus {
        if(filesId.isEmpty()){
            return TransactionCheckStatus.BlankParameterFailStatus
        }
        try{
            repository.deleteFiles(filesId)
        } catch (e: Exception){
            return TransactionCheckStatus.UnKnownFailStatus
        }
        return TransactionCheckStatus.SuccessStatus

    }
}
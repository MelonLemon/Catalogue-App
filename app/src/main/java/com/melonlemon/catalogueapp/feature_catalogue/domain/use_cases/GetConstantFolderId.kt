package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TransactionCheckStatus

class GetConstantFolderId(
    private val repository: CatalogueRepository
) {
    suspend operator fun invoke(): Int {

        return repository.getConstantFolderId()

    }
}
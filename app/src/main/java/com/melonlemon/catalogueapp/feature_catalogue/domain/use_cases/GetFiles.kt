package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetFiles(
    repository: CatalogueRepository
) {
     operator fun invoke(folderId:Int): Flow<List<CardInfo>> {

        return flowOf(emptyList())
    }
}
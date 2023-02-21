package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.ColumnInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class GetFileColumns(
    repository: CatalogueRepository
) {
    suspend operator fun invoke(fileId: Int): List<ColumnInfo> {

        return  listOf()
    }
}
package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.SelectedCategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository

class AddTagRecord(
    repository: CatalogueRepository
) {
    suspend operator fun invoke(fileId: Int, listOfNewTags: List<String>)  {

    }
}
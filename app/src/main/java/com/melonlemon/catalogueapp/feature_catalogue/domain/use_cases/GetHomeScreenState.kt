package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeScreenState

class GetHomeScreenState(
    repository: CatalogueRepository
) {
    suspend operator fun invoke(): HomeScreenState{

        return HomeScreenState()
    }
}
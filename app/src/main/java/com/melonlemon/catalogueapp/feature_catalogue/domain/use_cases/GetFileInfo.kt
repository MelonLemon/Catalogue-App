package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileInfoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetFileInfo(
    repository: CatalogueRepository
) {
    suspend operator fun invoke(fileId:Int): FileInfoState {

        return FileInfoState()
    }
}
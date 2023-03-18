package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.FileRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository

class GetFileCategories(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(sheetId: String, categoryColumnIndex: Int): List<String> {

        val letterColum = '@' + categoryColumnIndex
        val key = BuildConfig.API_KEY
        return  fileRepository.getCategories(
            spreadsheetId = sheetId,
            columnLetter = letterColum.toString(),
            apiKey = key
        )

    }
}
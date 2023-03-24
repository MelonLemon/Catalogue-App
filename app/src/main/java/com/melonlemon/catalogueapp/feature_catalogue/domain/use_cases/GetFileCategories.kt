package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository

class GetFileCategories(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(sheetId: String, categoryColumnIndex: Int): List<String> {

        val letterColum = '@' + categoryColumnIndex
        val key = BuildConfig.API_KEY
        val fileObject = fileRepository.getColumn(
            spreadsheetId = sheetId,
            columnLetter = letterColum.toString(),
            apiKey = key
        )
        val records = fileObject.values
        val columns = if(records.isNotEmpty()) records[0] else emptyList()
        return  columns.drop(1).distinct()

    }
}
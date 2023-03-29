package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository

class GetFileCategories(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(sheetId: String, categoryColumnIndex: Int): List<String> {

        //Add 1 as it's index not column number
        val letterColum = '@' + (categoryColumnIndex + 1)
        val key = BuildConfig.API_KEY
        val fileObject = fileRepository.getColumn(
            spreadsheetId = sheetId,
            columnLetter = letterColum.toString(),
            apiKey = key
        )
        val records = fileObject.values
        if(records!=null){
            val columns = if(records.isNotEmpty()) records[0] else emptyList()
            return  columns.drop(1).distinct()
        } else {
            return emptyList()
        }
    }
}
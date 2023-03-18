package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import kotlinx.coroutines.flow.*

class GetFileColumnTitles(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(sheetsId: String, numColumns: Int): List<String> {

        if(sheetsId.isNotBlank() && numColumns>0){
            val endRange = '@' + numColumns
            val key = BuildConfig.API_KEY

            val titles = fileRepository.getRecords(
                spreadsheetId = sheetsId,
                range = "A1:${endRange}1",
                apiKey = key
            ).first()
            return titles[0].toMutableList()
        }
        return emptyList()
    }
}
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

            val fileObject = fileRepository.getRecords(
                spreadsheetId = sheetsId,
                range = "A1:${endRange}1",
                apiKey = key
            ).first()
            if(fileObject.error==null && fileObject.values!=null){
                val titles =  fileObject.values[0]
                if(numColumns==titles.size){
                    return titles
                } else {
                   val noTitles = MutableList(numColumns - titles.size) { "" }
                   return titles + noTitles
                }
            }
        }
        return emptyList()
    }
}
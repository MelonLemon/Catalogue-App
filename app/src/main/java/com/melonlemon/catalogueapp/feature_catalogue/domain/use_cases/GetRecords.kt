package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import android.util.Log
import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.FileRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class GetRecords(
    private val fileRepository: FileRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(forRecordsState: ForRecordsState): Flow<List<List<String>>> {
        if(forRecordsState.fileId!=-1){
            val endRange = '@' + forRecordsState.numberOfColumns
            val key = BuildConfig.API_KEY
            val sheetsName = "'${forRecordsState.sheetsName}'"
            if(forRecordsState.isAllCategoriesSelected){
                val fileObject = fileRepository.getRecords(
                    spreadsheetId = forRecordsState.sheetId,
                    range = "$sheetsName!A2:$endRange",
                    apiKey = key
                )
                return fileObject.map { it.values }
            } else {
                //categoryColumn is number of column starts with 1(A, B, C etc)
                //That's why we have to minus 1
                return fileRepository.getRecords(
                    spreadsheetId = forRecordsState.sheetId,
                    range = "A:$endRange",
                    apiKey = key
                ).mapLatest { list ->
                    list.values.filter { it[forRecordsState.categoryColumn-1] in forRecordsState.listOfSelectedCategories}
                }
            }
        } else {
            return emptyFlow()
        }




}
}
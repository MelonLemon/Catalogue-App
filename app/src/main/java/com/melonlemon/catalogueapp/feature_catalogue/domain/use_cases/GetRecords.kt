package com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases

import android.util.Log
import com.melonlemon.catalogueapp.BuildConfig
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.FileRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordsResult
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.ValidationUrlCheckStatus
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.ForRecordsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class GetRecords(
    private val fileRepository: FileRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(forRecordsState: ForRecordsState): Flow<RecordsResult> {
        if(forRecordsState.fileId!=-1){
            val endRange = '@' + forRecordsState.numberOfColumns
            val key = BuildConfig.API_KEY
            val sheetsName = "'${forRecordsState.sheetsName}'"

            val fileObject = fileRepository.getRecords(
                spreadsheetId = forRecordsState.sheetId,
                range = "$sheetsName!A2:$endRange",
                apiKey = key
            )

            if(forRecordsState.isAllCategoriesSelected){

                return fileObject.map {
                    val error = it.error
                    val validationUrlCheckStatus = if(error==null) ValidationUrlCheckStatus.SuccessStatus else if(error.code == 403) {
                        ValidationUrlCheckStatus.NoRightsFailStatus
                    } else {
                        ValidationUrlCheckStatus.BrokenUrlFailStatus
                    }
                    RecordsResult(
                        records = it.values,
                        validationUrlCheckStatus = validationUrlCheckStatus
                    )

                }
            } else {
                //categoryColumn is number of column starts with 1(A, B, C etc)
                //That's why we have to minus 1
                return fileObject.mapLatest { it ->
                    val error = it.error
                    val validationUrlCheckStatus = if(error==null) ValidationUrlCheckStatus.SuccessStatus else if(error.code == 403) {
                        ValidationUrlCheckStatus.NoRightsFailStatus
                    } else {
                        ValidationUrlCheckStatus.BrokenUrlFailStatus
                    }

                    val records =  if(it.values != null) it.values.filter { it[forRecordsState.categoryColumn] in forRecordsState.listOfSelectedCategories}
                    else null
                    RecordsResult(
                        records = records,
                        validationUrlCheckStatus = validationUrlCheckStatus
                    )
                }
            }
        } else {
            return emptyFlow()
        }




}
}
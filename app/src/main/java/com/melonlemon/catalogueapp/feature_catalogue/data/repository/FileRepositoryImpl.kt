package com.melonlemon.catalogueapp.feature_catalogue.data.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileObject
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.data.data_source.api.GoogleSheetApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FileRepositoryImpl (
    private val googleSheetApiService: GoogleSheetApiService
): FileRepository {

    override fun getRecords(
        spreadsheetId: String,
        range: String,
        apiKey: String
    ) = flow {
        emit(googleSheetApiService.getAllRecords(spreadsheetId, range, apiKey))
    }

    override fun getPagingRecords(
        spreadsheetId: String,
        sheetsName: String,
        columnLetter: String,
        apiKey: String,
        page: Int,
        pageSize: Int
    )= flow {
        emit(googleSheetApiService.getPagingAllRecords(
            spreadsheetId = spreadsheetId,
            sheetsName = sheetsName,
            start = page*pageSize,
            end = page*pageSize + pageSize,
            columnLetter = columnLetter,
            apiKey = apiKey
        ))
    }

    override suspend fun getColumn(
        spreadsheetId: String,
        columnLetter: String,
        apiKey: String
    ): FileObject {
        return googleSheetApiService.getColumn(spreadsheetId, columnLetter, apiKey)
    }

    override suspend fun getFirstCell(
        spreadsheetId: String,
        sheetsName: String,
        apiKey: String
    ): FileObject {
        return googleSheetApiService.getFirstCell(
            spreadsheetId = spreadsheetId,
            sheetsName = sheetsName,
            apiKey = apiKey
        )
    }

}
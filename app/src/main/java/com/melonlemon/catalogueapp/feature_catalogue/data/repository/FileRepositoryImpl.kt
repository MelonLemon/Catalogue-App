package com.melonlemon.catalogueapp.feature_catalogue.data.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.GoogleSheetApiService
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

    override suspend fun getCategories(
        spreadsheetId: String,
        columnLetter: String,
        apiKey: String
    ): List<String> {
        return googleSheetApiService.getColumn(spreadsheetId, columnLetter, apiKey)
    }

}
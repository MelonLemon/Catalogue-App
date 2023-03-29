package com.melonlemon.catalogueapp.feature_catalogue.domain.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.FileObject
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun getRecords(spreadsheetId:String, range:String, apiKey: String): Flow<FileObject>

    fun getPagingRecords(spreadsheetId:String, sheetsName: String,
                         columnLetter:String, apiKey: String,
                         page: Int, pageSize: Int
    ): Flow<FileObject>

    suspend fun getColumn(spreadsheetId:String, columnLetter:String, apiKey: String): FileObject

    suspend fun getFirstCell(spreadsheetId:String, sheetsName:String, apiKey: String): FileObject
}
package com.melonlemon.catalogueapp.feature_catalogue.domain.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileInfoState
import kotlinx.coroutines.flow.Flow

interface CatalogueRepository {
    suspend fun getFolders(): List<CategoryInfo>

    fun getAllFiles(): Flow<List<CardInfo>>

    fun getFilesByFolderId(folderId:Int): Flow<List<CardInfo>>

    fun getAllRecords(fileId: Int): Flow<List<CardInfo>>

    fun getRecordsByTags(fileId: Int, tags: List<String>): Flow<List<CardInfo>>

    suspend fun addNewFolder(name: String)

    suspend fun getFileTitle(fileId:Int): String

    suspend fun getFileRecordTags(fileId:Int): List<String>

    suspend fun getRecord(fieldId: Int, recordId: Int): RecordInfo

    suspend fun updateRecord(fieldId: Int, recordInfo: RecordInfo)

    suspend fun addNewFile(fileInfo: FileInfo)

    suspend fun addNewRecord(recordInfo: RecordInfo)

    suspend fun getFileColumns(fieldId: Int): List<String>



}
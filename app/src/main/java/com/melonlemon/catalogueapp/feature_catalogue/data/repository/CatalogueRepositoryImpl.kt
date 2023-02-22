package com.melonlemon.catalogueapp.feature_catalogue.data.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.TestData
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileInfoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class CatalogueRepositoryImpl: CatalogueRepository {
    override suspend fun getFolders(): List<CategoryInfo> {
        val testData = TestData()
        return testData.folders
    }

    override fun getAllFiles(): Flow<List<CardInfo>> {
        val testData = TestData()
        return flowOf(testData.files)
    }

    override fun getFilesByFolderId(folderId: Int): Flow<List<CardInfo>> {
        val testData = TestData()
        return flowOf(testData.files.filter { it.hostId == folderId })
    }

    override fun getAllRecords(fileId: Int): Flow<List<CardInfo>> {
        val testData = TestData()
        return flowOf(testData.records.filter { it.hostId == fileId })
    }

    override fun getRecordsByTags(fileId: Int, tags: List<String>): Flow<List<CardInfo>> {
        val testData = TestData()
        return flowOf(testData.records.filter { it.hostId == fileId})
    }

    override suspend fun addNewFolder(name: String) {
        //Add New Folder
    }

    override suspend fun getFileTitle(fileId: Int): String {
        val testData = TestData()
        val title = testData.files.filter { it.id == fileId }
        return title[0].title
    }

    override suspend fun getFileRecordTags(fileId: Int): List<String> {
        val testData = TestData()
        val records = testData.records.filter { it.hostId == fileId }
        return records.map { it.tags }.flatten().distinct()
    }

    override suspend fun getRecord(fieldId: Int, recordId: Int): RecordInfo {
        val testData = TestData()
        val filesRecords = testData.filesRecords
        val recordsField = filesRecords[fieldId]
        val recordInfo =  recordsField?.filter { it.id == recordId}
        return recordInfo!![0]
    }

    override suspend fun updateRecord(fieldId: Int, recordInfo: RecordInfo) {

    }

    override suspend fun addNewFile(fileInfo: FileInfo) {

    }

    override suspend fun addNewRecord(recordInfo: RecordInfo) {

    }

    override suspend fun getFileColumns(fieldId: Int): List<String>  {

        return listOf("Recipe")
    }
}
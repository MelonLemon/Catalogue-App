package com.melonlemon.catalogueapp.feature_catalogue.domain.repository

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.*
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileInfoState
import kotlinx.coroutines.flow.Flow

interface CatalogueRepository {
    suspend fun getFolders(): List<Folders>

    fun getAllFiles(): Flow<List<Files>>

    fun getFilesByFolderId(folderId:Int): Flow<List<Files>>

    suspend fun addNewFolder(name: String)

    suspend fun addNewFile(files: Files)

    suspend fun getFileById(fileId: Int): Files

    suspend fun deleteFiles(filesId: List<Int>)

    suspend fun deleteFolder(folderId: Int)

    suspend fun getConstantFolderId(): Int
}
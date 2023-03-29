package com.melonlemon.catalogueapp.feature_catalogue.data.repository

import com.melonlemon.catalogueapp.feature_catalogue.data.data_source.room.CatalogueDao
import com.melonlemon.catalogueapp.feature_catalogue.data.util.CONSTANT_FOLDER_ID
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import kotlinx.coroutines.flow.Flow


class CatalogueRepositoryImpl(
    private val dao: CatalogueDao
): CatalogueRepository {
    override suspend fun addNewFolder(name: String) {
        dao.addFolder(Folders(id=null, name = name))
    }
    override suspend fun getFolders(): List<Folders> {
        return dao.getFolders()
    }
    override suspend fun deleteFolder(folderId: Int) {

        dao.deleteFolderWithChange(
            deletedFolder = folderId,
            newFolderId = CONSTANT_FOLDER_ID
        )
    }

    override suspend fun getConstantFolderId(): Int {
        return CONSTANT_FOLDER_ID
    }

    override fun getAllFiles(): Flow<List<Files>> {

        return dao.getFiles()
    }

    override fun getFilesByFolderId(folderId: Int): Flow<List<Files>> {
        return dao.getFilesByFolder(folderId)
    }

    override suspend fun addNewFile(file: Files) {
        dao.addFile(file)
    }

    override suspend fun getFileById(fileId: Int): Files {
        return dao.getFileById(fileId)
    }

    override suspend fun deleteFiles(filesId: List<Int>) {
        dao.deleteListOfFiles(filesId)
    }


}
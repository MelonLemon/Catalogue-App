package com.melonlemon.catalogueapp.feature_catalogue.data.data_source

import androidx.room.*
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Folders
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogueDao {

    //Get Folders
    @Query("SELECT * FROM folders")
    suspend fun getFolders():List<Folders>

    //Add New Folder
    @Upsert
    suspend fun addFolder(folder: Folders)

    //Delete Folder
    @Query("DELETE FROM folders WHERE folder_id=:folderId")
    suspend fun deleteFolder(folderId: Int)

    //Add File
    @Upsert
    suspend fun addFile(files: Files)

    //Delete File
    @Delete
    suspend fun deleteFile(files: Files)

    //Get Files
    @Query("SELECT * FROM files")
    fun getFiles():Flow<List<Files>>

    //Delete Files by Id
    @Query("DELETE FROM files WHERE file_id=:fileId")
    fun deleteFileById(fileId: Int)

    @Transaction
    suspend fun deleteListOfFiles(filesId: List<Int>){
        filesId.forEach { fileId ->
            deleteFileById(fileId)
        }
    }

    //Get Files By FolderId
    @Query("SELECT * FROM files WHERE folder_id=:folderId")
    fun getFilesByFolder(folderId: Int): Flow<List<Files>>

    //Get Files By FolderId
    @Query("SELECT * FROM files WHERE file_id=:fileId")
    fun getFileById(fileId: Int): Files


}
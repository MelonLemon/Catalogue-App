package com.melonlemon.catalogueapp.feature_catalogue.data.data_source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Folders
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoQueryTests {

    @get:Rule
    val archRule = InstantTaskExecutorRule()

    private lateinit var catalogueDatabase: CatalogueDatabase
    private lateinit var catalogueDao: CatalogueDao

    @Before
    fun setUp() {
        catalogueDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatalogueDatabase::class.java
        ).allowMainThreadQueries().build()
        catalogueDao = catalogueDatabase.catalogueDao
    }

    @Test
    fun addFolder_checkAdding() = runBlocking {
        val folderName = "News"
        val sampleFolder = Folders(id=null, name=folderName)
        val job = async { catalogueDao.getFolders().take(2).toList() }
        catalogueDao.addFolder(sampleFolder)

        val outputList = job.await()

        assertEquals(1, outputList.size)
        assertEquals(folderName, outputList[0].name)
    }

//    @Test
//    fun addFile_checkAdding() = runBlocking  {
//        val folderName = "News"
//        val fileName = "Sample"
//        val sampleFolder = Folders(id=1, name=folderName)
//        val sampleFile = Files(
//            id = null,
//            name = fileName,
//            folderId = 1,
//            sheetsId = "",
//            numColumns = 4,
//            titleColumnIndex = 1,
//            subHeaderColumnIndex = 2,
//            categoryColumnIndex = 3,
//            coverImg = "",
//            tags = listOf("Example", "Example2")
//        )
//        val job = async { catalogueDao.getFiles().take(2).toList() }
//        catalogueDao.addFolder(sampleFolder)
//        catalogueDao.addFile(sampleFile)
//
//        val outputList = job.await()
//
//        assertEquals(1, outputList.size)
//        assertEquals(fileName, outputList[0][0].name)
//    }

//    @Test
//    fun deleteFileById_checkDelete() = runBlocking  {
//
//    }
//
//    @Test
//    fun deleteListFiles_checkDelete() = runBlocking  {
//
//    }
//
//    @Test
//    fun getFileByFolder_checkSelection() = runBlocking  {
//
//    }
//
//    @Test
//    fun getFileByFileId_checkSelection() = runBlocking  {
//
//    }

    @After
    fun tearDown() {
        catalogueDatabase.close()
    }
}
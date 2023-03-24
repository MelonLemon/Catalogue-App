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
import app.cash.turbine.test
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Folders
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoQueryTests {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

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

    //Passed
    @Test
    fun addFolder_checkAdding() = runBlocking {
        val folderName = "News"
        val sampleFolder = Folders(id=null, name=folderName)
        catalogueDao.addFolder(sampleFolder)

        val outputList = catalogueDao.getFolders()

        assertEquals(1, outputList.size)
        assertEquals(folderName, outputList[0].name)
    }

    //Passed
    @Test
    fun addFile_checkAdding() = runBlocking  {
        val folderName = "News"
        val fileName = "Sample"
        val sampleFolder = Folders(id=1, name=folderName)
        val sampleFile = Files(
            id = 1,
            name = fileName,
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        catalogueDao.addFolder(sampleFolder)
        catalogueDao.addFile(sampleFile)
        catalogueDao.getFiles().test{
            val list = awaitItem()
            assert(list.size==1)
            assert(list.contains(sampleFile))
            cancel()
        }
    }

    //Passed
    @Test
    fun deleteFileById_checkDelete() = runBlocking  {
        val folderName = "News"
        val fileName = "Sample"
        val sampleFolder = Folders(id=1, name=folderName)
        val sampleFile = Files(
            id = 1,
            name = fileName,
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        val sampleFile2 = Files(
            id = 2,
            name = fileName + "1",
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        catalogueDao.addFolder(sampleFolder)
        catalogueDao.addFile(sampleFile)
        catalogueDao.addFile(sampleFile2)
        catalogueDao.deleteFileById(1)

        catalogueDao.getFiles().test{
            val list = awaitItem()
            assert(list.size == 1)
            assert(list.contains(sampleFile2))
            cancel()
        }

    }

    //Passed
    @Test
    fun deleteListFiles_checkDelete() = runBlocking  {
        val folderName = "News"
        val fileName = "Sample"
        val sampleFolder = Folders(id=1, name=folderName)
        val sampleFile = Files(
            id = 1,
            name = fileName,
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        val sampleFile2 = Files(
            id = 2,
            name = fileName + "1",
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        val sampleFile3 = Files(
            id = 3,
            name = fileName + "2",
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        catalogueDao.addFolder(sampleFolder)
        catalogueDao.addFile(sampleFile)
        catalogueDao.addFile(sampleFile2)
        catalogueDao.addFile(sampleFile3)
        catalogueDao.deleteListOfFiles(listOf(1,3))

        catalogueDao.getFiles().test{
            val list = awaitItem()
            assert(list.size == 1)
            assert(list.contains(sampleFile2))
            cancel()
        }
    }

    //Passed
    @Test
    fun getFileByFolder_checkSelection() = runBlocking  {
        val folderName = "News"
        val folderName2 = "Music"
        val fileName = "Right Folder"
        val fileName2 = "Wrong Folder"
        val sampleFolder = Folders(id=1, name=folderName)
        val sampleFolder2 = Folders(id=2, name=folderName2)
        val sampleFile = Files(
            id = 1,
            name = fileName,
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        val sampleFile2 = Files(
            id = 2,
            name = fileName2,
            folderId = 2,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        catalogueDao.addFolder(sampleFolder)
        catalogueDao.addFolder(sampleFolder2)
        catalogueDao.addFile(sampleFile)
        catalogueDao.addFile(sampleFile2)
        catalogueDao.getFilesByFolder(2).test {
            val list = awaitItem()
            assert(list.size == 1)
            assert(list.contains(sampleFile2))
            cancel()
        }
    }

    //Passed
    @Test
    fun getFileByFileId_checkSelection() = runBlocking  {
        val folderName = "News"
        val folderName2 = "Music"
        val fileName = "Right Folder"
        val fileName2 = "Wrong Folder"
        val sampleFolder = Folders(id=1, name=folderName)
        val sampleFolder2 = Folders(id=2, name=folderName2)
        val sampleFile = Files(
            id = 1,
            name = fileName,
            folderId = 1,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )
        val sampleFile2 = Files(
            id = 2,
            name = fileName2,
            folderId = 2,
            sheetsId = "",
            sheetsName = "",
            numColumns = 4,
            titleColumnIndex = 1,
            subHeaderColumnIndex = 2,
            categoryColumnIndex = 3,
            coverImg = "",
            tags = listOf("Example", "Example2")
        )

        catalogueDao.addFolder(sampleFolder)
        catalogueDao.addFolder(sampleFolder2)
        catalogueDao.addFile(sampleFile)
        catalogueDao.addFile(sampleFile2)

        val output = catalogueDao.getFileById(1)
        assert(output.id==1)
    }

    @After
    fun tearDown() {
        catalogueDatabase.close()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
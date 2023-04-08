package com.melonlemon.catalogueapp.feature_catalogue.data.data_source.room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.melonlemon.catalogueapp.feature_catalogue.data.util.CONSTANT_FOLDER_ID
import com.melonlemon.catalogueapp.feature_catalogue.data.util.CONSTANT_FOLDER_NAME
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Folders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider

class DatabaseInitializer(
    private val catalogueProvider: Provider<CatalogueDao>
): RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        populateFolders()
        populateFiles()
    }

    private suspend fun populateFolders() {
        catalogueProvider.get().addFolder(
            Folders(id=0, name="News")
        )
        catalogueProvider.get().addFolder(
            Folders(id=1, name="Music")
        )
        catalogueProvider.get().addFolder(
            Folders(id=2, name="Movies")
        )
        catalogueProvider.get().addFolder(
            Folders(id=CONSTANT_FOLDER_ID, name=CONSTANT_FOLDER_NAME)
        )
    }
    private suspend fun populateFiles() {
        catalogueProvider.get().addFile(
            Files(
                id=0,
                name="Youtube Music Artists",
                folderId = 1,
                sheetsId = "1ZLxdPbjzrA-lDLjVuYvvsu_zFdIzlwPxymD-qZtvOr4",
                sheetsName = "Top Youtube Artists",
                numColumns = 13,
                titleColumnIndex = 0,
                subHeaderColumnIndex = 10,
                categoryColumnIndex = 12,
                covImgRecColumnIndex = 4,
                coverImg = "https://images.unsplash.com/photo-1458560871784-56d23406c091?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
                tags = listOf("Youtube", "Music")
            )
        )
        catalogueProvider.get().addFile(
            Files(
                id=1,
                name="Tv-series and Shows",
                folderId = 2,
                sheetsId = "1BSJ0-8dWzSM-TUBeNp-KYGFs5uTmJ4WPIsW3ENdvxqQ",
                sheetsName = "Tv-series",
                numColumns = 10,
                titleColumnIndex = 0,
                subHeaderColumnIndex = 5,
                categoryColumnIndex = 6,
                covImgRecColumnIndex = 2,
                coverImg = "https://images.unsplash.com/photo-1586899028174-e7098604235b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1742&q=80",
                tags = listOf("Tv-series")
            )
        )
        catalogueProvider.get().addFile(
            Files(
                id=2,
                name="Astronomy Picture of the Day",
                folderId = 3,
                sheetsId = "1N-tIjv34uIOEosAExNkkSWCd9n--ybTVpgOxiHeGoZE",
                sheetsName = "Astronomy Picture of the Day",
                numColumns = 5,
                titleColumnIndex = 1,
                subHeaderColumnIndex = 0,
                categoryColumnIndex = 2,
                covImgRecColumnIndex = 3,
                coverImg = "https://images.unsplash.com/photo-1484589065579-248aad0d8b13?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=759&q=80",
                tags = listOf("Photo", "Space")
            )
        )
    }
}
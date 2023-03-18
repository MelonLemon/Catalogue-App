package com.melonlemon.catalogueapp.feature_catalogue.data.data_source

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
            Folders(id=3, name="Others")
        )
    }
    private suspend fun populateFiles() {
        catalogueProvider.get().addFile(
            Files(
                id=0,
                name="Music for sleep",
                folderId = 1,
                sheetsId = "1ZLxdPbjzrA-lDLjVuYvvsu_zFdIzlwPxymD-qZtvOr4",
                numColumns = 4,
                titleColumnIndex = 1,
                subHeaderColumnIndex = 4,
                categoryColumnIndex = 3,
                covImgRecColumnIndex = 2,
                coverImg = "https://images.unsplash.com/photo-1444312645910-ffa973656eba?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
                tags = listOf("Meditation", "Music")
            )
        )
        catalogueProvider.get().addFile(
            Files(
                id=1,
                name="Korean Food",
                folderId = 3,
                sheetsId = "1BSJ0-8dWzSM-TUBeNp-KYGFs5uTmJ4WPIsW3ENdvxqQ",
                numColumns = 4,
                titleColumnIndex = 1,
                subHeaderColumnIndex = 4,
                categoryColumnIndex = 4,
                covImgRecColumnIndex = 2,
                coverImg = "https://images.unsplash.com/photo-1590301157890-4810ed352733?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=736&q=80",
                tags = listOf("Korea", "Food")
            )
        )
        catalogueProvider.get().addFile(
            Files(
                id=2,
                name="Nasa",
                folderId = 3,
                sheetsId = "1N-tIjv34uIOEosAExNkkSWCd9n--ybTVpgOxiHeGoZE",
                numColumns = 3,
                titleColumnIndex = 1,
                subHeaderColumnIndex = 2,
                categoryColumnIndex = 2,
                covImgRecColumnIndex = 3,
                coverImg = "https://images.unsplash.com/photo-1484589065579-248aad0d8b13?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=759&q=80",
                tags = listOf("Photo", "Space")
            )
        )
    }
}
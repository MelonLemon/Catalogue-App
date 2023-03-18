package com.melonlemon.catalogueapp.feature_catalogue.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Files
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.Folders
import com.melonlemon.catalogueapp.feature_catalogue.domain.util.Converters
import java.io.File

@Database(
    entities = [Folders::class, Files::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CatalogueDatabase: RoomDatabase() {

    abstract val catalogueDao: CatalogueDao

    companion object {
        const val DATABASE_NAME = "catalogue_db"
    }

}
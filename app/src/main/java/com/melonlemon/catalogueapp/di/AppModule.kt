package com.melonlemon.catalogueapp.di

import android.app.Application
import androidx.room.Room
import com.melonlemon.catalogueapp.feature_catalogue.data.data_source.room.CatalogueDao
import com.melonlemon.catalogueapp.feature_catalogue.data.data_source.room.CatalogueDatabase
import com.melonlemon.catalogueapp.feature_catalogue.data.data_source.room.DatabaseInitializer
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.CatalogueRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.data.repository.FileRepositoryImpl
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.CatalogueRepository
import com.melonlemon.catalogueapp.feature_catalogue.domain.repository.FileRepository
import com.melonlemon.catalogueapp.feature_catalogue.data.data_source.api.GoogleSheetApiService
import com.melonlemon.catalogueapp.feature_catalogue.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatalogueDatabase(
//        @ApplicationContext context: Context,
        catalogueProvider: Provider<CatalogueDao>,
        app: Application
    ): CatalogueDatabase {
        return Room.databaseBuilder(
            app,
            CatalogueDatabase::class.java,
            CatalogueDatabase.DATABASE_NAME
        ).addCallback(
            DatabaseInitializer(catalogueProvider)
        ).build()
    }

    @Provides
    @Singleton
    fun provideCatalogueDao(db: CatalogueDatabase): CatalogueDao = db.catalogueDao

    @Provides
    @Singleton
    fun provideCatalogueRepository(db: CatalogueDatabase): CatalogueRepository {
        return CatalogueRepositoryImpl(db.catalogueDao)
    }

    @Provides
    fun baseUrl() = "https://sheets.googleapis.com/v4/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): GoogleSheetApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(GoogleSheetApiService::class.java)

    @Provides
    @Singleton
    fun provideFileRepository(apiService: GoogleSheetApiService): FileRepository {
        return FileRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCatalogueUseCases(repository: CatalogueRepository, apiRepository: FileRepository): CatalogueUseCases {
        return CatalogueUseCases(
            getFolders = GetFolders(repository),
            addNewFolder = AddNewFolder(repository),
            getFilteredList = GetFilteredList(),
            getFiles = GetFiles(repository),
            getRecords = GetRecords(apiRepository),
            getFileColumnTitles = GetFileColumnTitles(apiRepository),
            addNewFile = AddNewFile(repository),
            getFileCategories = GetFileCategories(apiRepository),
            getInfoForLoading = GetInfoForLoading(repository),
            getFilteredFiles = GetFilteredFiles(),
            deleteFiles = DeleteFiles(repository),
            deleteFolder = DeleteFolder(repository),
            getFirstRow = GetFirstRow(apiRepository),
            getFileById = GetFileById(repository),
            getConstantFolderId = GetConstantFolderId(repository),
            checkUrlValidation = CheckUrlValidation(apiRepository)
        )
    }

}
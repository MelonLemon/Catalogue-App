package com.melonlemon.catalogueapp.feature_catalogue.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "folders")
data class Folders(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "folder_id", index = true) val id: Int?= null,
    val name: String
)

@Entity(tableName = "files",
    foreignKeys = [ForeignKey(
        entity = Folders::class,
        parentColumns = ["folder_id"],
        childColumns = ["folder_id"]
    )]
)
data class Files(
    @PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "file_id", index = true)val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "folder_id") val folderId: Int,
    @ColumnInfo(name = "sheets_id") val sheetsId: String,
    @ColumnInfo(name = "num_columns") val numColumns: Int,
    val titleColumnIndex: Int,
    val subHeaderColumnIndex: Int,
    val categoryColumnIndex: Int,
    val covImgRecColumnIndex: Int?=null,
    val coverImg: String,
    val tags: List<String>
)

//Supported Tables


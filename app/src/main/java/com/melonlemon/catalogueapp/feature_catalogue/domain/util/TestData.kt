package com.melonlemon.catalogueapp.feature_catalogue.domain.util

import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CardInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.ColumnInfo
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.RecordInfo

class TestData {

    val folders = listOf<CategoryInfo>(
        CategoryInfo(id = 1, name = "News"),
        CategoryInfo(id = 2, name = "Music"),
        CategoryInfo(id = 3, name = "Movies"),
        CategoryInfo(id = 4, name = "Food"),
        CategoryInfo(id = 5, name = "No Category")
    )
    val files = listOf(
        CardInfo(
            id = 1,
            hostId = 4,
            title = "Сoaster for a cup",
            tags = listOf("GreenTea", "Herbal"),
            photoPath = null
        ),
        CardInfo(
            id = 2,
            hostId = 2,
            title = "Meditation Music",
            tags = listOf("Meditation", "Music"),
            photoPath = null
        ),
        CardInfo(
            id = 3,
            hostId = 4,
            title = "Korean Food",
            tags = listOf("Korea", "Food"),
            photoPath = null
        ),
    )

    val filesRecords = mapOf(
        1 to listOf<RecordInfo>(RecordInfo(
            id = 1,
            name = "Сoaster for a cup",
            urlString = "",
            columnsInfo = listOf(ColumnInfo(1, "Recipes", "1 tps of green tea + 23ml milk")),
            tags = listOf("GreenTea", "Herbal"),
        )),
        2 to listOf<RecordInfo>(RecordInfo(
            id = 1,
            name = "Meditation Music",
            urlString = "",
            columnsInfo = listOf(ColumnInfo(1, "Styles", "lofi and sift indie")),
            tags = listOf("Meditation", "Music")
        )),
        3 to listOf<RecordInfo>(
            RecordInfo(
                id = 1,
                name = "Kimchi",
                urlString = "",
                columnsInfo = listOf(ColumnInfo(1, "Recipes", "Cabage + hot chilli powder")),
                tags = listOf("Spicy", "Side Dishes")
        ),
            RecordInfo(
                id = 2,
                name = "Ramen",
                urlString = "",
                columnsInfo = listOf(ColumnInfo(1, "Recipes", "just buy ramen")),
                tags = listOf("No Category")
            ),
            RecordInfo(
                id = 3,
                name = "Hot chicken",
                urlString = "",
                columnsInfo = listOf(ColumnInfo(1, "Recipes", "Chicken, Sauce")),
                tags = listOf("Spicy", "Chicken")
            )
        ),
    )

    val records = listOf(
        CardInfo(
            id = 1,
            hostId = 1,
            title = "Сoaster for a cup",
            tags = listOf("GreenTea", "Herbal"),
            photoPath = null
        ),
        CardInfo(
            id = 1,
            hostId = 2,
            title = "Meditation Music",
            tags = listOf("Meditation", "Music"),
            photoPath = null
        ),
        CardInfo(
            id = 1,
            hostId = 3,
            title = "Kimchi",
            tags = listOf("Spicy", "Side Dishes"),
            photoPath = null
        ),
        CardInfo(
            id = 2,
            hostId = 3,
            title = "Ramen",
            tags = listOf("No Category"),
            photoPath = null
        ),
        CardInfo(
            id = 3,
            hostId = 3,
            title = "Hot chicken",
            tags = listOf("Spicy", "Chicken"),
            photoPath = null
        )
    )

}
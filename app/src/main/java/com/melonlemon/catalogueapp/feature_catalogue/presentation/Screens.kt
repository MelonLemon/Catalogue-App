package com.melonlemon.catalogueapp.feature_catalogue.presentation

sealed class Screens(val route: String){
    object AuthenticationFileScreen: Screens("authentication_screen")
    object AddEditFileScreen: Screens("add_content_file_screen")
    object FileScreen: Screens("file_screen")
    object AddFolderScreen: Screens("add_folder_screen")
    object HomeScreen: Screens("home_screen")
    object RecordScreen: Screens("record_screen")
}

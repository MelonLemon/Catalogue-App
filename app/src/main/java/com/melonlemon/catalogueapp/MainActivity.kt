package com.melonlemon.catalogueapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.melonlemon.catalogueapp.feature_catalogue.presentation.Screens
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.AddEditFileScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.AddEditFileViewModel
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.AuthenticationFileScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.FileViewModel
import com.melonlemon.catalogueapp.feature_catalogue.presentation.file.RecordScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.AddFolderScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeScreen
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.HomeViewModel
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatalogueAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screens.HomeScreen.route){
                        //Double 2 screens: Home + Folder
                        composable(route = Screens.HomeScreen.route){
                            val viewModel = hiltViewModel<HomeViewModel>()
                            HomeScreen(
                                onAddNewFileClick = { navController.navigate(Screens.AuthenticationFileScreen.route) },
                                onFolderBtnClick = { navController.navigate(Screens.AddFolderScreen.route) },
                                onFileClick = { fileId, title ->
                                    navController.navigate("${Screens.FileScreen.route}?fileId=${fileId}&title=${title}")},
                                viewModel = viewModel
                            )
                        }
                        composable(route = Screens.AddFolderScreen.route){
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry(
                                    Screens.HomeScreen.route
                                )
                            }
                            val parentViewModel = hiltViewModel<HomeViewModel>(
                                parentEntry
                            )

                            AddFolderScreen(
                                onArrowBackClick = { navController.popBackStack() },
                                viewModel = parentViewModel
                            )
                        }
                        //Double 2 screens: authen + content
                        composable(
                            route = "${Screens.AuthenticationFileScreen.route}?fileId={fileId}",
                            arguments = listOf(
                                navArgument("fileId") {  defaultValue = -1 },
                            )
                        ){
                            val viewModel = hiltViewModel<AddEditFileViewModel>()
                            AuthenticationFileScreen(
                                nextBtnClick = { navController.navigate(Screens.AddEditFileScreen.route) },
                                backBtn= { navController.popBackStack() },
                                viewModel = viewModel
                            )
                        }
                        composable(
                            route = Screens.AddEditFileScreen.route
                        ){
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry(
                                    "${Screens.AuthenticationFileScreen.route}?fileId={fileId}"
                                )
                            }
                            val parentViewModel = hiltViewModel<AddEditFileViewModel>(
                                parentEntry
                            )
                            AddEditFileScreen(
                                saveBtnClick = { navController.navigate(Screens.HomeScreen.route) },
                                backBtnClick = { navController.popBackStack() },
                                viewModel = parentViewModel
                            )
                        }
                        composable(
                            route = "${Screens.FileScreen.route}?fileId={fileId}&title={title}",
                            arguments = listOf(
                                navArgument("fileId") {  defaultValue = -1 },
                                navArgument("title") {  type = NavType.StringType }
                            )
                        ){
                            val viewModel = hiltViewModel<FileViewModel>()
                            FileScreen(
                                backBtnClick = { navController.popBackStack() },
                                onRecordClick = { navController.navigate(Screens.RecordScreen.route) },
                                onEditClick = { fileId -> navController.navigate("${Screens.AuthenticationFileScreen.route}?fileId=${fileId}") },
                                viewModel = viewModel
                            )
                        }
                        composable(
                            route = Screens.RecordScreen.route){
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry(
                                    "${Screens.FileScreen.route}?fileId={fileId}&title={title}"
                                )
                            }
                            val parentViewModel = hiltViewModel<FileViewModel>(
                                parentEntry
                            )
                            RecordScreen(
                                backBtnClick = { navController.popBackStack() },
                                viewModel = parentViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatalogueAppTheme {
        Greeting("Android")
    }
}
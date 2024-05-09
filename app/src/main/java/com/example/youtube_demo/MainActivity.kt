package com.example.youtube_demo

import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.youtube_demo.ui.theme.Youtube_demoTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

import io.github.jan.supabase.storage.Storage


val supabase = createSupabaseClient(
    supabaseUrl = "https://tdwzdjjjdzdoxbpbsese.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRkd3pkampqZHpkb3hicGJzZXNlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUwMDQ4NjYsImV4cCI6MjAzMDU4MDg2Nn0.t015IwE_GN1q1w0dRAsvctxEz_SZiwPN3nQeov59C1U"
) {
    install(Postgrest)
    install(Storage)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Youtube_demoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {val viewModel = viewModel<MainViewModel>()

                    AppNavHost(navController = rememberNavController(),viewModel)
                }
            }
        }
    }
}

enum class Screen {
    VideoList,
    VideoPlayer,
}
sealed class NavigationItem(val route: String) {
    object VideoList : NavigationItem(Screen.VideoList.name)
    object VideoPlayer : NavigationItem(Screen.VideoPlayer.name)

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = Screen.VideoList.name){
        composable(Screen.VideoList.name){
            ViedoList(viewModel,navController)
        }
        composable(Screen.VideoPlayer.name+"/{id}",arguments = listOf( navArgument("id"){
            type = NavType.IntType
        })){
            val id = it.arguments!!.getInt("id")
            Log.d("Video", "AppNavHost_id: $id")
            PlayerPage(viewModel,id){
                navController.popBackStack()
            }
        }
    }
}
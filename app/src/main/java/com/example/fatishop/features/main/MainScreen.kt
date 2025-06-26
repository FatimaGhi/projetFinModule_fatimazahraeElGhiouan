package com.example.fatishop.features.main


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fatishop.shared.navigation.BottomNavGraph
import com.example.fatishop.shared.navigation.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

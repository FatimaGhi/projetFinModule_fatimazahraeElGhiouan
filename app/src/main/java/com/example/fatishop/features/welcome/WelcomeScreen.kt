package com.example.fatishop.features.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fatishop.shared.utils.Routes
import com.example.fatishop.shared.utils.firestore.dummyPerfumes
import com.example.fatishop.shared.utils.firestore.uploadProductsToFirestore

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Routes.LOGIN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate(Routes.REGISTER) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
//            @Composable
//            fun UploadButton() {
            // hado dyl product bax nzidhom fe firestore da9a wo7da hhh m3gaza ana
                Button(onClick = { uploadProductsToFirestore(dummyPerfumes) }) {
                    Text("Upload Products to Firestore")
//                }
            }
        }
    }
}

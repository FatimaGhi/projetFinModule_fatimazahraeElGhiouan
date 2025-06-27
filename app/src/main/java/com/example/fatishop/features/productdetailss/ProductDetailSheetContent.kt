package com.example.fatishop.features.productdetailss

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fatishop.shared.model.Product
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fatishop.features.home.HomeViewModel
import com.example.fatishop.shared.repository.ProductRepository

//@Composable
//fun ProductDetailSheetContent(product: Product) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Text(product.name, style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text("Brand: ${product.brand}")
//        Spacer(modifier = Modifier.height(8.dp))
//        Text("Price: ${product.price} DH", color = MaterialTheme.colorScheme.primary)
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(product.description)
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { /* TODO: Add to cart */ }) {
//            Text("Add to Cart")
//        }
//    }
//}



@Composable
fun ProductDetailSheetContent(
    product: Product,
    onDismiss: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var quantity by remember { mutableStateOf(1) }
    val isLoading = remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val currentUser = FirebaseAuth.getInstance().currentUser

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Product Information
        Text(
            text = product.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Brand: ${product.brand}")
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "%.2f DH".format(product.price),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = product.description ?: "")
        Spacer(modifier = Modifier.height(24.dp))

        // Quantity selector
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Quantity:", modifier = Modifier.padding(end = 16.dp))
            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                Icon(Icons.Default.Remove, contentDescription = "Decrease")
            }
            Text("$quantity", modifier = Modifier.padding(horizontal = 8.dp))
            IconButton(onClick = { quantity++ }) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }

        // Add to Cart button
        Button(
            onClick = {
                currentUser?.let { user ->
                    viewModel.addToCart(
                        userId = user.uid,
                        product = product,
                        quantity = quantity,
                        isLoading = isLoading,
                        onSuccess = {
                            showSuccessDialog = true
                        }
                    )
                } ?: run {
                    showErrorDialog = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading.value
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Add to Cart", fontSize = 16.sp)
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = { Text("Product added to cart successfully.") },
            confirmButton = {
                Button(onClick = {
                    showSuccessDialog = false
                    onDismiss()
                }) {
                    Text("OK")
                }
            }
        )
    }

    // Error Dialog
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = {
                Text(
                    if (currentUser != null)
                        "Failed to add the product to the cart."
                    else
                        "Please log in to add items to your cart."
                )
            },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

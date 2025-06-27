package com.example.fatishop.features.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fatishop.shared.model.CartItem
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""
) {
    val cartItems = viewModel.cartItems

    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            viewModel.loadCart(userId)
        }
    }

    val total = cartItems.sumOf { it.price * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                text = "My Cart",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        // Liste des articles
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onIncrease = { viewModel.increaseQuantity(userId, item.productId) },
                    onDecrease = { viewModel.decreaseQuantity(userId, item.productId) }
                )
            }
        }

        // Section Total & Paiement
        CheckoutSection(total = total.toDouble())
    }
}

@Composable
private fun CartItemCard(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "%.2f MAD".format(item.price),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            QuantitySelector(
                quantity = item.quantity,
                onIncrease = onIncrease,
                onDecrease = onDecrease
            )
        }
    }
}

@Composable
private fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = onDecrease,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(Icons.Default.Remove, null)
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(24.dp),
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = onIncrease,
            modifier = Modifier.size(36.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Icon(Icons.Default.Add, null)
        }
    }
}

@Composable
private fun CheckoutSection(total: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total:", style = MaterialTheme.typography.titleLarge)
            Text(
                "%.2f MAD".format(total),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Checkout action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Checkout", style = MaterialTheme.typography.titleMedium)
        }
    }
}

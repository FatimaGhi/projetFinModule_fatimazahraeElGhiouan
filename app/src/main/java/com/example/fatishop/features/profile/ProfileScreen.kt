package com.example.fatishop.features.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.example.fatishop.features.cart.CartViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fatishop.shared.model.Order

@Composable
fun ProfileScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val email = FirebaseAuth.getInstance().currentUser?.email ?: "Invité"

    val orders by viewModel.getOrdersForUser(userId).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Section profile
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Mon Profil", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section commandes
        Text(
            text = "Mes Commandes",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (orders.isEmpty()) {
            Text("Aucune commande trouvée", color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order = order)
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun OrderCard(order: Order) {
    val formattedDate = order.timestamp?.toDate()?.let {
        SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault()).format(it)
    } ?: "Date inconnue"

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Nom: ${order.name}", style = MaterialTheme.typography.bodyLarge)
            Text("Téléphone: ${order.phone}")
            Text("Adresse: ${order.address}")
            Text("Montant total: ${order.total} DH")
            Text(
                "Status: ${order.status}",
                color = when (order.status) {
                    "Pending" -> MaterialTheme.colorScheme.primary
                    "Delivered" -> MaterialTheme.colorScheme.secondary
                    "Cancelled" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            Text("Date: $formattedDate", style = MaterialTheme.typography.bodySmall)
        }
    }
}

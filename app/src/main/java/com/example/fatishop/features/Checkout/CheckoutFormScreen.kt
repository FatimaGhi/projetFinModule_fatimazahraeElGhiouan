package com.example.fatishop.features.Checkout

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fatishop.features.cart.CartViewModel // Import ajouté

@Composable
fun CheckoutFormScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel(),
    onOrderSuccess: () -> Unit
) {
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    // ✅ ضروري نحمل السلة هنا قبل ما نستعمل cartItems
    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            viewModel.loadCart(userId)
        }
    }

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("") }
    var showPayment by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 🏠 معلومات الزبون
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom complet") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Adresse") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Téléphone") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!showPayment) {
            Button(
                onClick = {
                    if (name.isNotBlank() && address.isNotBlank() && phone.isNotBlank()) {
                        showPayment = true
                    } else {
                        Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Passer au paiement")
            }
        } else {
            // 💳 طرق الأداء
            PaymentOption(
                title = "Carte bancaire",
                icon = Icons.Default.CreditCard,
                selected = selectedPayment == "card",
                onClick = { selectedPayment = "card" }
            )

            PaymentOption(
                title = "Paiement à la livraison",
                icon = Icons.Default.Money,
                selected = selectedPayment == "cash",
                onClick = { selectedPayment = "cash" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.checkoutWithAddress(
                        userId, name, address, phone,
                        onSuccess = {
                            Toast.makeText(context, "Commande passée avec succès", Toast.LENGTH_LONG).show()
                            onOrderSuccess()
                        },
                        onFailure = { e ->
                            Toast.makeText(context, "Erreur: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                enabled = selectedPayment.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmer la commande")
            }
        }
    }
}


@Composable
fun PaymentOption(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, modifier = Modifier.weight(1f))
        RadioButton(selected = selected, onClick = onClick)
    }
}

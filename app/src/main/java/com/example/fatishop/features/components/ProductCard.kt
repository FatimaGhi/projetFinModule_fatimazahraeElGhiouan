package com.example.fatishop.features.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fatishop.shared.model.Product

@Composable
fun ProductCard(product: Product) {
    val context = LocalContext.current
    val imageResId = remember(product.imageUrl) {
        context.resources.getIdentifier(product.imageUrl, "drawable", context.packageName)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Navigate to details */ },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${product.price} MAD",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

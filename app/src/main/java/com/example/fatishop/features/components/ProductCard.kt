package com.example.fatishop.features.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fatishop.features.home.RatingBar
import com.example.fatishop.shared.model.Product
import androidx.compose.material3.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.unit.size




//@Composable
//fun ProductCard(product: Product) {
//    val context = LocalContext.current
//    val imageResId = remember(product.imageUrl) {
//        context.resources.getIdentifier(product.imageUrl, "drawable", context.packageName)
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { /* TODO: Navigate to details */ },
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(8.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = imageResId),
//                contentDescription = product.name,
//                modifier = Modifier
//                    .height(120.dp)
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp),
//                contentScale = ContentScale.Crop
//            )
//
//            Text(
//                text = product.name,
//                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = product.brand,
//                style = MaterialTheme.typography.bodySmall
//            )
//            Text(
//                text = "${product.price} MAD",
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.SemiBold
//            )
//        }
//    }
//}

@Composable
fun ProductCard(
    product: Product,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        onClick = onItemClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                val context = LocalContext.current
                val imageResId = remember(product.imageUrl) {
                    context.resources.getIdentifier(product.imageUrl, "drawable", context.packageName)
                }

                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Favorite icon
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                        .clickable(onClick = onFavoriteClick)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite ?: false) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Popular badge
                if (product.isPopular == true) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Popular",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Stock indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${product.inStock} in stock",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }

            // Product details
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = product.brand,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = product.description ?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "%.2f DH".format(product.price),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        RatingBar(rating = product.rating)
                        Text(
                            text = "(${"%.1f".format(product.rating)})",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }

                // Type badge
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            when (product.type) {
                                "Woman" -> Color(0xFFFFF0F5)
                                "Man" -> Color(0xFFF0F8FF)
                                else -> Color(0xFFF0FFF0)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = product.type ?: "",
                        color = when (product.type) {
                            "Woman" -> Color(0xFFC71585)
                            "Man" -> Color(0xFF1E90FF)
                            else -> Color(0xFF2E8B57)
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
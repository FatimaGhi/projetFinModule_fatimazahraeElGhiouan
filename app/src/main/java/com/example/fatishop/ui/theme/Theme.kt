package com.example.fatishop.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color





val PrimaryColor = Color(0xFF6A1B9A)  // لون بنفسجي أنيق
val PrimaryLightColor = Color(0xFF9C4DFF)
val PrimaryDarkColor = Color(0xFF38006B)
val SecondaryColor = Color(0xFFF48FB1)  // لون وردي
val SecondaryLightColor = Color(0xFFFFC1E3)
val SecondaryDarkColor = Color(0xFFBF5F82)
val BackgroundColor = Color(0xFFF5F5F5)  // خلفية فاتحة
val SurfaceColor = Color.White
val ErrorColor = Color(0xFFD32F2F)
val OnPrimaryColor = Color.White
val OnSecondaryColor = Color.Black
val OnBackgroundColor = Color.Black
val OnSurfaceColor = Color.Black
val OnErrorColor = Color.White
val TextPrimary = Color(0xFF212121)
val TextSecondary = Color(0xFF757575)
val LightGray = Color(0xFFEEEEEE)


//fin

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


)

@Composable
fun FatiShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
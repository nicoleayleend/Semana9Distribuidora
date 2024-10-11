package com.example.distribalimentos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color.Blue,
    secondary = Color.Green,
    background = Color.White,
    surface = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color.Blue,
    secondary = Color.Green,
    background = Color.Black,
    surface = Color.Black
)

@Composable
fun DistribAlimentosTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DistribAlimentosTheme {
        // Your composable preview content here
    }
}

package com.coreai.iality.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = Primary,

    background = Background,

    surface = Surface,

    onPrimary = Surface,

    onBackground = TextPrimary,

    onSurface = TextPrimary,

    error = Error

)

@Composable
fun IALITYTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColors,
        typography = IALITYTypography,
        content = content
    )

}
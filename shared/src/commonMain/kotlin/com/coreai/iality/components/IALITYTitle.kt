package com.coreai.iality.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun IALITYTitle(text: String) {

    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge
    )

}
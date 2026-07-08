package com.coreai.iality.components

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardHeader(
    nombre: String = "Carlos"
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "☰",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "IALITY",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "👤",
            style = MaterialTheme.typography.headlineSmall
        )
    }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {

        Text(
            text = "Hola, $nombre 👋",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Sistema Activo",
            color = MaterialTheme.colorScheme.primary
        )
    }
}
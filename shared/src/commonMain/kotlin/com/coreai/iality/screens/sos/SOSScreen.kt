package com.coreai.iality.screens.sos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalUriHandler
import com.coreai.iality.components.IALITYTextField

@Composable
fun SOSScreen() {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var numeroContacto by remember { mutableStateOf("") }

    // Manejador oficial de Compose Multiplatform para abrir enlaces externos
    val uriHandler = LocalUriHandler.current

    // Coordenadas fijas simuladas para la ubicación
    val latitudPrueba = "19.4326"
    val longitudPrueba = "-99.1332"
    val mensajeBase = "¡NECESITO AYUDA EMERGENCIA! Mi ubicación actual es: https://maps.google.com/?q=$latitudPrueba,$longitudPrueba"

    // Formateamos los espacios para que el enlace sea válido en la web
    val mensajeFormateado = mensajeBase.replace(" ", "%20")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚨 Emergencia",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Presiona el botón para enviar una alerta.",
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        // Campo de texto limpio usando el componente de tu equipo
        IALITYTextField(
            value = numeroContacto,
            onValueChange = { numeroContacto = it },
            label = "Número de contacto de emergencia"
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.error)
                .clickable {
                    if (numeroContacto.isNotBlank()) {
                        mostrarDialogo = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "SOS",
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("🚨 Enviar alerta") },
            text = { Text("Selecciona cómo deseas enviar la alerta de emergencia al número: $numeroContacto") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                        val numeroLimpio = numeroContacto.replace(" ", "").replace("+", "")
                        try {
                            // Abre WhatsApp con el chat del número y el mensaje de auxilio ya escrito
                            uriHandler.openUri("https://api.whatsapp.com/send?phone=$numeroLimpio&text=$mensajeFormateado")
                        } catch (e: Exception) {
                            // Evita cierres inesperados si ocurre un error
                        }
                    }
                ) {
                    Text("🟢 WhatsApp")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                        try {
                            // Abre la app de SMS del teléfono con el destinatario y el mensaje con el mapa listos
                            uriHandler.openUri("sms:$numeroContacto?body=$mensajeFormateado")
                        } catch (e: Exception) {
                            // Evita cierres inesperados
                        }
                    }
                ) {
                    Text("📱 SMS")
                }
            }
        )
    }
}
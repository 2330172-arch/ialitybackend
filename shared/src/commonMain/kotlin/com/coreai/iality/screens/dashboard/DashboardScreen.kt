package com.coreai.iality.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coreai.iality.components.DashboardHeader
import com.coreai.iality.session.SessionManager

@Composable
fun DashboardScreen() {
    val usuario = SessionManager.currentUser
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        DashboardHeader(
            nombre = usuario?.nombre ?: "Usuario"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            shape = RoundedCornerShape(24.dp)
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "🤖",
                        style = MaterialTheme.typography.displayLarge
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "CORE IA",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Asistente Activo",
                        color = MaterialTheme.colorScheme.primary
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {

            Text("🚨 SOS")

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
        ) {

            Text("🔔 Recordatorios")

        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Card(
                modifier = Modifier.weight(1f)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text("Próxima alerta")

                    Text("Sin pendientes")

                }

            }

            Card(
                modifier = Modifier.weight(1f)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text("Estado")

                    Text("En línea")

                }

            }

        }

    }

}
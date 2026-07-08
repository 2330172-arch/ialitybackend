package com.coreai.iality.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import com.coreai.iality.navigation.AppState
import com.coreai.iality.navigation.Screen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coreai.iality.components.*
import com.coreai.iality.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen() {

    val viewModel = remember { RegisterViewModel() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IALITYLogo()

        IALITYSpacer(40)

        IALITYTitle("Crear cuenta")

        IALITYSpacer(8)

        IALITYSubtitle("Regístrate para comenzar")

        IALITYSpacer(35)

        IALITYTextField(
            value = viewModel.nombre,
            onValueChange = viewModel::onNombreChange,
            label = "Nombre completo"
        )

        IALITYSpacer(16)

        IALITYTextField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            label = "Correo electrónico"
        )

        IALITYSpacer(16)

        IALITYTextField(
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            label = "Contraseña"
        )

        IALITYSpacer(16)

        IALITYTextField(
            value = viewModel.confirmPassword,
            onValueChange = viewModel::onConfirmPasswordChange,
            label = "Confirmar contraseña"
        )

        IALITYSpacer(30)

        IALITYButton(
            text = "Crear cuenta",
            onClick = {
                viewModel.registrar()
            }
        )
        IALITYSpacer(16)

        Text(
            text = viewModel.mensaje,
            color = MaterialTheme.colorScheme.primary
        )
        IALITYSpacer(20)

        IALITYDivider()

        IALITYSpacer(20)

        Text(
            text = "Ya tengo una cuenta",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                AppState.currentScreen = Screen.LOGIN
            }
        )
    }
}
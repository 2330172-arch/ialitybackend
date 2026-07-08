package com.coreai.iality.screens.auth

import com.coreai.iality.navigation.AppState
import androidx.compose.foundation.clickable
import com.coreai.iality.navigation.Screen
import androidx.compose.foundation.layout.Arrangement
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
import com.coreai.iality.viewmodel.LoginViewModel



@Composable
fun LoginScreen() {

    val viewModel = remember { LoginViewModel() }

    LaunchedEffect(viewModel.loginCorrecto) {

        if (viewModel.loginCorrecto) {



            AppState.currentScreen = Screen.DASHBOARD
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IALITYLogo()

        IALITYSpacer(40)

        IALITYTitle("Bienvenido")

        IALITYSpacer(8)

        IALITYSubtitle("Inicia sesión para continuar")

        IALITYSpacer(35)

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

        IALITYSpacer(30)

        IALITYButton(
            text = "Iniciar sesión",
            onClick = {
                viewModel.login()
            }

        )
        IALITYSpacer(16)

        Text(
            text = viewModel.mensaje,
            color = MaterialTheme.colorScheme.primary
        )

        IALITYSpacer(25)

        IALITYDivider()

        IALITYSpacer(20)

        Text(
            text = "Crear una cuenta",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                AppState.currentScreen = Screen.REGISTER
            }
        )
    }
}
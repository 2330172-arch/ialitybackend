package com.coreai.iality.navigation

import androidx.compose.runtime.Composable
import com.coreai.iality.screens.auth.LoginScreen
import com.coreai.iality.screens.auth.RegisterScreen
import com.coreai.iality.screens.dashboard.DashboardScreen

@Composable
fun AppNavigation() {

    when (AppState.currentScreen) {

        Screen.LOGIN -> LoginScreen()

        Screen.REGISTER -> RegisterScreen()

        Screen.DASHBOARD -> DashboardScreen()

    }

}
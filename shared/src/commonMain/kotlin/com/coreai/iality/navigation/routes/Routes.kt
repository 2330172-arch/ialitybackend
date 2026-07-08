package com.coreai.iality.navigation.routes

sealed class Routes(val route: String) {

    data object Splash : Routes("splash")

    data object Login : Routes("login")

    data object Register : Routes("register")

    data object Dashboard : Routes("dashboard")

    data object Reminder : Routes("reminder")

    data object SOS : Routes("sos")

    data object Profile : Routes("profile")

    data object Settings : Routes("settings")
}
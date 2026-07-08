package com.coreai.iality.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class Screen {
    LOGIN,
    REGISTER,
    DASHBOARD
}

object AppState {

    var currentScreen by mutableStateOf(Screen.LOGIN)

}
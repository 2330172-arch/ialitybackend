package com.coreai.iality.session

import androidx.compose.runtime.mutableStateOf
import com.coreai.iality.model.User

object SessionManager {

    var currentUser: User? = null
        private set

    var showLoginNotification = mutableStateOf(false)

    fun login(user: User) {
        currentUser = user
        showLoginNotification.value = true
    }

    fun notificationShown() {
        showLoginNotification.value = false
    }

    fun logout() {
        currentUser = null
    }
}
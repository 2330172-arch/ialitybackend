package com.coreai.iality.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.coreai.iality.model.LoginRequest
import com.coreai.iality.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.coreai.iality.session.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel {

    private val repository = AuthRepository()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    var cargando by mutableStateOf(false)
        private set

    var loginCorrecto by mutableStateOf(false)
        private set

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun login() {

        cargando = true

        CoroutineScope(Dispatchers.Main).launch {

            try {

                val usuario = repository.login(
                    LoginRequest(
                        correo = email,
                        password = password
                    )
                )

                SessionManager.login(usuario)

                loginCorrecto = true
                mensaje = usuario.nombre





            } catch (e: Exception) {

                loginCorrecto = false
                mensaje = e.message ?: "Error"

            }

            cargando = false
        }
    }
}
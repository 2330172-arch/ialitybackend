package com.coreai.iality.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.coreai.iality.model.User
import com.coreai.iality.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel {

    private val repository = AuthRepository()

    var nombre by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var mensaje by mutableStateOf("")
        private set

    var cargando by mutableStateOf(false)
        private set

    fun onNombreChange(value: String) {
        nombre = value
    }

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value
    }

    fun registrar() {

        cargando = true

        CoroutineScope(Dispatchers.Main).launch {

            try {

                val respuesta = repository.register(
                    User(
                        nombre = nombre,
                        correo = email,
                        password = password,
                        foto = null
                    )
                )

                mensaje = respuesta.mensaje

            } catch (e: Exception) {

                mensaje = e.message ?: "Error"

            }

            cargando = false
        }
    }
}
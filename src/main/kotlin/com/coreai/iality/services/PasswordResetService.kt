package com.coreai.iality.service

import com.coreai.iality.repository.PasswordResetRepository
import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlin.random.Random
import java.util.Properties

class PasswordResetService(
    private val repository: PasswordResetRepository = PasswordResetRepository()
) {

    fun generarCodigo(correo: String): String {

        val codigo = Random.nextInt(100000, 1000000).toString()

        val expiracion =
            System.currentTimeMillis() + (10 * 60 * 1000)

        val creado =
            repository.crearCodigo(
                correo = correo,
                codigo = codigo,
                expiracion = expiracion
            )

        if (!creado) {
            throw IllegalArgumentException(
                "El correo no está registrado."
            )
        }

        return codigo
    }

    fun verificarCodigo(
        correo: String,
        codigo: String
    ): Boolean {

        return repository.verificarCodigo(
            correo = correo,
            codigo = codigo
        )
    }

    fun cambiarPassword(
        correo: String,
        codigo: String,
        nuevaPassword: String
    ): Boolean {

        return repository.cambiarPassword(
            correo = correo,
            codigo = codigo,
            nuevaPassword = nuevaPassword
        )
    }

    fun enviarCorreo(
        correo: String,
        codigo: String
    ) {

        val smtpHost =
            System.getenv("SMTP_HOST")
                ?: "smtp.gmail.com"

        val smtpPort =
            System.getenv("SMTP_PORT")
                ?: "587"

        val smtpUser =
            System.getenv("SMTP_USER")
                ?: throw IllegalStateException(
                    "SMTP_USER no configurado"
                )

        val smtpPassword =
            System.getenv("SMTP_PASSWORD")
                ?: throw IllegalStateException(
                    "SMTP_PASSWORD no configurado"
                )

        val smtpFrom =
            System.getenv("SMTP_FROM")
                ?: smtpUser

        val properties = Properties()

        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.smtp.host"] = smtpHost
        properties["mail.smtp.port"] = smtpPort

        val session =
            Session.getInstance(
                properties,
                object : Authenticator() {

                    override fun getPasswordAuthentication():
                            PasswordAuthentication {

                        return PasswordAuthentication(
                            smtpUser,
                            smtpPassword
                        )
                    }
                }
            )

        val message =
            MimeMessage(session)

        message.setFrom(
            InternetAddress(smtpFrom)
        )

        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(correo)
        )

        message.subject =
            "Código de recuperación - IALITY"

        message.setText(
            """
            Hola,

            Tu código para recuperar tu contraseña de IALITY es:

            $codigo

            Este código tiene una duración de 10 minutos.

            Si tú no solicitaste este cambio, ignora este correo.

            IALITY
            """.trimIndent()
        )

        Transport.send(message)
    }
}
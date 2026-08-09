package com.coreai.iality.services

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

class EmailService {

    private val smtpHost =
        System.getenv("SMTP_HOST") ?: "smtp.gmail.com"

    private val smtpPort =
        System.getenv("SMTP_PORT") ?: "587"

    private val smtpUser =
        System.getenv("SMTP_USER")
            ?: error("Falta SMTP_USER en Railway")

    private val smtpPassword =
        System.getenv("SMTP_PASSWORD")
            ?: error("Falta SMTP_PASSWORD en Railway")

    fun enviarCodigo(
        correoDestino: String,
        codigo: String
    ) {

        val properties = Properties().apply {

            put("mail.smtp.auth", "true")

            put("mail.smtp.starttls.enable", "true")

            put("mail.smtp.host", smtpHost)

            put("mail.smtp.port", smtpPort)

            put("mail.smtp.connectiontimeout", "10000")

            put("mail.smtp.timeout", "10000")

            put("mail.smtp.writetimeout", "10000")
        }

        val session = Session.getInstance(
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

        val message = MimeMessage(session)

        message.setFrom(
            InternetAddress(smtpUser, "IALITY")
        )

        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(correoDestino)
        )

        message.subject = "Código para recuperar tu contraseña - IALITY"

        message.setText(
            """
            Hola,

            Tu código para recuperar tu contraseña de IALITY es:

            $codigo

            Este código tiene una duración de 10 minutos.

            Si tú no solicitaste recuperar tu contraseña,
            puedes ignorar este correo.

            IALITY
            """.trimIndent()
        )

        Transport.send(message)
    }
}
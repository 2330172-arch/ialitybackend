package com.coreai.iality.repository

import com.coreai.iality.model.ApiResponse
import com.coreai.iality.model.User
import com.coreai.iality.network.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import com.coreai.iality.model.LoginRequest
class AuthRepository {

    suspend fun register(user: User): ApiResponse {

        return HttpClientProvider.client
            .post("http://192.168.101.21:8080/registro") {

                contentType(ContentType.Application.Json)

                setBody(user)
            }
            .body()
    }

    suspend fun login(request: LoginRequest): User {

        return HttpClientProvider.client
            .post("http://192.168.101.21:8080/login") {

                contentType(ContentType.Application.Json)

                setBody(request)

            }.body()
    }
}
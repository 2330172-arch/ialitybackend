package com.coreai.iality

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
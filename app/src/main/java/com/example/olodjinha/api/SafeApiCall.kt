package com.example.olodjinha.api

import retrofit2.HttpException

object SafeApiCall {

    suspend fun <T> safeNetworkRequest(block: suspend () -> T): T? {
        return try {
            block()
        } catch (ex: Exception) {
            println(ex.message)
            println("Yacov")
            null
        }
    }
}
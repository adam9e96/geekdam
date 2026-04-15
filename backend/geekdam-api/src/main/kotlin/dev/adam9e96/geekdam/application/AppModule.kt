package dev.adam9e96.geekdam.application

import dev.adam9e96.geekdam.application.config.loadNasaConfig
import dev.adam9e96.geekdam.application.config.readApiKey
import dev.adam9e96.geekdam.application.plugins.configureHTTP
import dev.adam9e96.geekdam.application.plugins.configureMonitoring
import dev.adam9e96.geekdam.application.plugins.configureRouting
import dev.adam9e96.geekdam.application.plugins.configureSerialization
import dev.adam9e96.geekdam.feature.apod.ApodService
import dev.adam9e96.geekdam.infrastructure.nasa.NasaApodClient
import dev.adam9e96.geekdam.infrastructure.nasa.NasaHttpClientFactory
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

/**
 * Ktor 애플리케이션의 진입점.
 * 공통 플러그인과 기능 라우팅을 여기서 조립.
 */
fun Application.module() {
    val nasaConfig = loadNasaConfig()
    val nasaApodClient = NasaApodClient(
        httpClient = NasaHttpClientFactory.create(),
        apiKey = nasaConfig.readApiKey(),
        baseUrl = nasaConfig.baseUrl,
    )
    val apodService = ApodService(
        nasaApodClient = nasaApodClient,
    )

    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting(apodService)
}

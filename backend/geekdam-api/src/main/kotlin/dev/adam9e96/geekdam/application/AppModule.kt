package dev.adam9e96.geekdam.application

import dev.adam9e96.geekdam.application.config.NasaConfig
import dev.adam9e96.geekdam.application.config.loadNasaConfig
import dev.adam9e96.geekdam.application.config.readApiKey
import dev.adam9e96.geekdam.application.plugins.configureHTTP
import dev.adam9e96.geekdam.application.plugins.configureMonitoring
import dev.adam9e96.geekdam.application.plugins.configureRouting
import dev.adam9e96.geekdam.application.plugins.configureSerialization
import dev.adam9e96.geekdam.feature.apod.ApodService
import dev.adam9e96.geekdam.infrastructure.nasa.NasaApodClient
import dev.adam9e96.geekdam.infrastructure.nasa.NasaHttpClientFactory
import dev.adam9e96.geekdam.infrastructure.persistence.database.DatabaseFactory
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

/**
 * Ktor 애플리케이션의 진입점.
 * 공통 플러그인과 기능 라우팅을 여기서 조립.
 */
fun Application.module() {
    val sqlitePath = environment.config
        .propertyOrNull("geekdam.database.sqlite-path")
        ?.getString()
        ?: "./data/geekdam.db"

    // 애플리케이션 시작 시 SQLite 연결과 기본 스키마 생성을 수행
    DatabaseFactory.init(sqlitePath)

    // NASA 관련 설정과 클라이언트 생성은 실제 APOD 기능이 호출될 때까지 지연합니다.
    // 이렇게 하면 NASA 환경변수가 없어도 루트와 health 엔드포인트는 정상적으로 기동할 수 있습니다.
    val nasaApodClientProvider: () -> NasaApodClient = {
        val nasaConfig: NasaConfig = loadNasaConfig()

        NasaApodClient(
            httpClient = NasaHttpClientFactory.create(),
            apiKey = nasaConfig.readApiKey(),
            baseUrl = nasaConfig.baseUrl,
        )
    }

    val apodService = ApodService(
        nasaApodClientProvider = nasaApodClientProvider,
    )

    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting(apodService)
}

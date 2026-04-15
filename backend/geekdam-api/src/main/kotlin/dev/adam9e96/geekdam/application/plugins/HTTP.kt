package dev.adam9e96.geekdam.application.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.plugins.defaultheaders.*

/**
 * HTTP 레벨 공통 설정입니다.
 * 압축, 조건부 헤더, 기본 응답 헤더를 여기서 설정합니다.
 */
fun Application.configureHTTP() {
    install(ConditionalHeaders)
    install(Compression)
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }
}

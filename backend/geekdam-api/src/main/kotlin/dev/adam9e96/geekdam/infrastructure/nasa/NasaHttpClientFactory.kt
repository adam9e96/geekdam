package dev.adam9e96.geekdam.infrastructure.nasa

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * NASA API 호출에 사용할 Ktor HttpClient 생성 팩토리.
 * 외부 JSON 응답을 안정적으로 처리하기 위한 기본 설정을 포함.
 */
object NasaHttpClientFactory {

    fun create(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        // NASA 응답에 예상하지 못한 필드가 추가되어도 파싱이 깨지지 않게 함.
                        ignoreUnknownKeys = true
                        // 응답 JSON을 조금 더 유연하게 해석하기 위한 기본 설정.
                        explicitNulls = false
                    },
                )
            }
        }
}

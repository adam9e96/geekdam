package dev.adam9e96.geekdam.application.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

/**
 * 직렬화 관련 설정을 두는 자리입니다.
 * 이후 JSON, Protobuf 설정을 이 파일에 확장합니다.
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            Json {
                // 외부 API나 프론트 응답에 예기치 않은 필드가 있어도 유연하게 처리합니다.
                ignoreUnknownKeys = true
                // null 값은 기본적으로 생략해 응답을 조금 더 단순하게 유지합니다.
                explicitNulls = false
                // 개발 중 사람이 읽기 쉬운 JSON 출력을 원할 때 유용합니다.
                prettyPrint = true
            },
        )
    }
}

package dev.adam9e96.geekdam.application.config

import io.ktor.server.application.Application

/**
 * NASA API 연동에 필요한 설정 값.
 * 실제 API 키 값은 OS 환경변수에서 읽고, YAML에는 환경변수 이름만 둠.
 */
data class NasaConfig(
    val apiKeyEnvName: String, // NASA API 키가 들어있는 환경변수 이름.
    val baseUrl: String, // NASA API 기본 URL.
)

/**
 * application.yaml 설정과 OS 환경변수를 조합해 NASA 설정을 만듬.
 */
fun Application.loadNasaConfig(): NasaConfig {
    val apiKeyEnvName = environment.config
        .propertyOrNull("geekdam.nasa.api-key-env")
        ?.getString()
        ?: "NASA_API_KEY"

    val baseUrl = environment.config
        .propertyOrNull("geekdam.nasa.base-url")
        ?.getString()
        ?: "https://api.nasa.gov"

    val apiKey: String? = System.getenv(apiKeyEnvName)

    require(!apiKey.isNullOrBlank()) {
        "환경변수 '$apiKeyEnvName' 가 비어 있거나 설정되지 않았습니다. Windows 사용자 환경변수에 NASA API 키를 추가해주세요."
    }

    return NasaConfig(
        apiKeyEnvName = apiKeyEnvName,
        baseUrl = baseUrl,
    )
}

/**
 * 실제 NASA API 키 값을 환경변수에서 읽어옴.
 */
fun NasaConfig.readApiKey(): String =
    requireNotNull(System.getenv(apiKeyEnvName)) {
        "환경변수 '$apiKeyEnvName' 를 찾을 수 없습니다."
    }

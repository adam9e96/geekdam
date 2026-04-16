package dev.adam9e96.geekdam.feature.health.dto

import kotlinx.serialization.Serializable

/**
 * 서버 상태 확인용 응답 DTO입니다.
 */
@Serializable
data class HealthResponse(
    val status: String, // 서버 상태값입니다. 현재는 단순히 ok 를 사용합니다.
)

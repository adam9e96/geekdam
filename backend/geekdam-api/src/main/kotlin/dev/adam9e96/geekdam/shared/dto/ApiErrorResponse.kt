package dev.adam9e96.geekdam.shared.dto

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

/**
 * API 에러 응답을 일관된 JSON 형태로 내려주기 위한 공통 DTO입니다.
 */
@Serializable
data class ApiErrorResponse(
    val code: String, // 프론트나 로그에서 분기하기 위한 에러 코드입니다.
    val message: String, // 사용자 또는 개발자가 읽을 에러 메시지입니다.
    val status: Int, // HTTP 상태코드 숫자값입니다.
    val path: String, // 에러가 발생한 요청 경로입니다.
    val timestamp: String, // 에러 발생 시각입니다.
)

/**
 * 현재 시각을 ISO-8601 문자열로 반환합니다.
 */
fun nowIsoString(): String = OffsetDateTime.now().toString()

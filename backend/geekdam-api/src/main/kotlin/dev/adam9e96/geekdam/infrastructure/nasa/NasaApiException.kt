package dev.adam9e96.geekdam.infrastructure.nasa

/**
 * NASA API 호출 실패를 표현하는 전용 예외.
 * 상태코드와 응답 본문 일부를 함께 담아 디버깅.
 */
class NasaApiException(
    val statusCode: Int,
    val responseBody: String,
    message: String,
) : RuntimeException(message)

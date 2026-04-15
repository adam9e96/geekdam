package dev.adam9e96.geekdam.domain.apod

import java.time.LocalDate

/**
 * Geekdam 내부에서 APOD를 다룰 때 사용하는 도메인 모델.
 * APOD API에서 제공하는 JSON 형태를 현재는 그대로 사용하나 유연하게 바꿔서 사용가능
 */
data class ApodItem(
    val title: String, // 오늘의 우주 사진 제목
    val description: String, // 화면과 저장에 사용할 설명문
    val date: LocalDate, // APOD가 가리키는 기준 날짜
    val mediaType: ApodMediaType, // 내부에서 안전하게 다루기 위한 미디어 타입
    val mediaUrl: String, // 기본 표시용 이미지 또는 영상 URL
    val hdMediaUrl: String?, // 고해상도 원본 URL
    val copyright: String?, // 저작권 표기 정보
)

/**
 * NASA의 media_type 값을 내부에서 안전하게 다루기 위한 타입.
 */
enum class ApodMediaType {
    IMAGE,
    VIDEO,
    UNKNOWN,
}
